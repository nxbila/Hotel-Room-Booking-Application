package com.upgrad.BookingService.services;

import com.upgrad.BookingService.dao.BookingDao;
import com.upgrad.BookingService.dto.BookingDto;
import com.upgrad.BookingService.dto.PaymentDto;
import com.upgrad.BookingService.entities.BookingInfoEntity;
import com.upgrad.BookingService.exceptions.InvalidBookingIdException;
import com.upgrad.BookingService.exceptions.InvalidPaymentModeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingDao bookingDao;

    @Autowired // Autowired RestTemplate
    private RestTemplate restTemplate;

    @Value("${endPoint3CalledPaymentServiceUrl}")
    private String endPoint3CalledpaymentServiceUrl;

    // Receive the Booking request details, Generate Random room numbers,
    // calculate total Room Price, set bookedOn date and save
    @Override
    public BookingInfoEntity recvBookingInformation(BookingDto bookingDto) {
        String roomNumbers = GenerateRandomRoomNumbers(bookingDto.getNumOfRooms());
        LocalDate fromDate = convertStringToLocalDate(bookingDto.getFromDate());
        LocalDate toDate = convertStringToLocalDate(bookingDto.getToDate());
        int numDays = (int) Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();

        //1000 INR is the base price/day/room.
        int roomPrice = 1000 * bookingDto.getNumOfRooms() * numDays;

        LocalDateTime bookedOn = LocalDateTime.now();

        BookingInfoEntity bookingInformation = new BookingInfoEntity();
        bookingInformation.setFromDate(fromDate.atStartOfDay());
        bookingInformation.setToDate(toDate.atStartOfDay());
        bookingInformation.setAadharNumber(bookingDto.getAadharNumber());
        bookingInformation.setNumOfRooms(bookingDto.getNumOfRooms());
        bookingInformation.setRoomNumbers(roomNumbers);
        bookingInformation.setRoomPrice(roomPrice);
        bookingInformation.setBookedOn(bookedOn);

        return  bookingDao.save(bookingInformation);
    }

    // Generate random room numbers based on count
    public static String GenerateRandomRoomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        //return list of room numbers as a String
        String roomListString = numberList.toString();

        // removing the square braces [ ]
        roomListString = roomListString.substring(1, roomListString.length()-1);
        return roomListString;
    }

    // Utility to Convert String to LocalDate
    public static LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    @Override
    public BookingInfoEntity createTransactionInfo(PaymentDto paymentDto)
            throws InvalidPaymentModeException, InvalidBookingIdException {

        String paymentMode = paymentDto.getPaymentMode();
        boolean paymentModeBool;

        paymentModeBool =  paymentMode.equalsIgnoreCase("card") || paymentMode.equalsIgnoreCase("upi");

        if(!paymentModeBool) {
            throw new InvalidPaymentModeException("Invalid Mode of Payment.");
        }
        Optional<BookingInfoEntity> booking = bookingDao.findById(paymentDto.getBookingId());
        booking.orElseThrow(() -> new InvalidBookingIdException("Invalid Booking Id"));

        BookingInfoEntity bookingInformation = booking.get();

                 // Create Map
                Integer transactionId = restTemplate.postForObject(endPoint3CalledpaymentServiceUrl, paymentDto, Integer.class);
                if(transactionId==null) {
                     return null;
                }
                bookingInformation.setTransactionId(transactionId);
                bookingInformation = bookingDao.save(bookingInformation);
                String confirmString = "Booking confirmed for user with aadhaar number: "
                        + bookingInformation.getAadharNumber()
                        +    "    |    "
                        + "Here are the booking details:    " + bookingInformation.toString();
                System.out.println(confirmString);
                return bookingInformation;
            }
    }