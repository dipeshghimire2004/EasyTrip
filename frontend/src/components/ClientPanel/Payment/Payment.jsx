import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import api from "../../API/Api"; // Import the Axios instance

export default function Payment() {
  const location = useLocation();
  const { startDate: initialStartDate, endDate: initialEndDate, pricePerNight, guesthousesid } = location.state || {}; // Extract price per night

  const [isBooked, setIsBooked] = useState(false);
  const [isCancel, setIsCancel] = useState(false);
  const [startDate, setStartDate] = useState(initialStartDate || "");
  const [endDate, setEndDate] = useState(initialEndDate || "");

  const today = new Date().toISOString().split("T")[0]; // Get today's date in YYYY-MM-DD format

  const isButtonDisabled =
    !startDate ||
    !endDate ||
    new Date(startDate) < new Date(today) || // Prevents past dates
    new Date(startDate) >= new Date(endDate); // Prevents same start and end date

  const handleConfirmBooking = async () => {
    const numberOfDays = calculateNumberOfDays();
    const totalPrice = calculateTotalPrice();

    // Prepare the booking data
    const bookingData = {
      guesthouseId: guesthousesid,
      checkInDate: startDate,
      checkOutDate: endDate,
      totalPrice: totalPrice,
    };

    try {
      console.log(bookingData);
      const response = await api.post("/api/bookings", bookingData); // Use axios API instance to send POST request
      if (response.status === 200) {
        setIsBooked(true);
        setIsCancel(true); // Enable the Cancel button
        alert("Booking Confirmed! Thank you for choosing EasyTrip.");
      } else {
        alert("Booking failed. Please try again.");
      }
    } catch (error) {
      alert("An error occurred. Please try again later.");
      console.error(error);
    }
  };

  const handleCancelBooking = () => {
    alert("Booking Canceled!");
    setIsCancel(false); // Disable the Cancel button
    setIsBooked(false); // Re-enable the Confirm button
  };

  // Calculate the number of days between startDate and endDate
  const calculateNumberOfDays = () => {
    if (startDate && endDate) {
      const start = new Date(startDate);
      const end = new Date(endDate);
      const diffInTime = end - start;
      return diffInTime / (1000 * 3600 * 24); // Days between start and end
    }
    return 0;
  };

  // Calculate the total price based on price per night and number of days
  const calculateTotalPrice = () => {
    const numberOfDays = calculateNumberOfDays();
    return numberOfDays * pricePerNight;
  };

  const numberOfDays = calculateNumberOfDays();
  const totalPrice = calculateTotalPrice();

  return (
    <div className="p-4 max-w-lg mx-auto">
      {!isBooked ? (
        <h1 className="text-2xl font-bold mb-4">Booking & Payment</h1>
      ) : (
        <p className="text-green-600 text-xl font-bold mb-4">Booking Confirmed! Thank you for choosing EasyTrip.</p>
      )}

      <div className="mb-4">
        <label className="font-semibold">Select Dates:</label>
        <div className="flex gap-2 mt-2">
          <input
            type="date"
            className="border p-2 rounded-md"
            value={startDate}
            min={today} // Prevent selecting past dates
            onChange={(e) => setStartDate(e.target.value)}
          />
          <input
            type="date"
            className="border p-2 rounded-md"
            value={endDate}
            min={startDate || today} // Ensure end date is not before start date
            onChange={(e) => setEndDate(e.target.value)}
          />
        </div>
        {startDate && endDate && new Date(startDate) >= new Date(endDate) && (
          <p className="text-red-500 text-sm mt-1">End date must be after start date.</p>
        )}
      </div>

      <div className="mb-4">
        <h2 className="font-bold">Total Price Breakdown</h2>
        <p>Base Price (per night): ${pricePerNight}</p>
        <p>Duration: {numberOfDays} {numberOfDays === 1 ? 'night' : 'nights'}</p>
        <p>Total Price: ${totalPrice}</p>
      </div>

      <div className="mb-4">
        <h2 className="font-bold">Payment Options:</h2>
        <p>Cash</p>
      </div>

      <button
        className={`p-2 mb-2 rounded-md w-full ${isButtonDisabled || isBooked ? "bg-gray-400 cursor-not-allowed" : "bg-green-500 text-white"}`}
        onClick={handleConfirmBooking}
        disabled={isButtonDisabled || isBooked}
      >
        Confirm Booking
      </button>

      <button
        className={`p-2 rounded-md w-full ${!isCancel || isButtonDisabled ? "bg-gray-400 cursor-not-allowed" : "bg-red-500 text-white"}`}
        onClick={handleCancelBooking}
        disabled={!isCancel || isButtonDisabled}
      >
        Cancel Booking
      </button>
    </div>
  );
}
