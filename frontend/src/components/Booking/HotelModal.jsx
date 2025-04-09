import React from "react";
import { useNavigate } from "react-router-dom";

const HotelModal = ({
  hotel,
  closeModal,
  startDate,
  setStartDate,
  endDate,
  setEndDate,
  checkAvailability,
  availabilityChecked,
  isAvailable,
  handleBooking,
}) => {
  const today = new Date().toISOString().split("T")[0]; // Get today's date in YYYY-MM-DD format

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50 backdrop-blur-md">
      <div className="bg-white p-6 rounded-md max-w-lg w-full relative">
        <button
          className="absolute top-2 right-2 bg-red-500 text-white p-1 rounded-md"
          onClick={closeModal}
        >
          Close
        </button>
        <div className="bg-gray-300 h-48 mb-4"></div>
        <h2 className="font-bold text-xl mb-2">{hotel.name}</h2>
        <p className="mb-2">
          A lovely guesthouse located in the heart of {hotel.location}.
        </p>
        <p className="mb-2">Price: ${hotel.price} per night</p>
        <p className="mb-2">Room: {hotel.roomType}</p>
        <p className="mb-2">Rating: {hotel.rating} Stars</p>
        <p className="mb-4">Amenities: {hotel.amenities.join(", ")}</p>

        {/* Availability Selection */}
        <div className="mb-4">
          <p className="font-semibold mb-2">Select Dates for Availability:</p>
          <div className="flex gap-2">
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
          <button
            className="bg-purple-500 text-white p-2 mt-2 rounded-md"
            onClick={checkAvailability}
          >
            Check Availability
          </button>
          {availabilityChecked && (
            <p className="mt-2 font-semibold">
              {isAvailable
                ? "Room is available for the selected dates."
                : "Room is NOT available. Please select valid dates."}
            </p>
          )}
        </div>

        {/* Book Now Button */}
        <button
          className={`${
            isAvailable ? "bg-blue-500" : "bg-gray-400 cursor-not-allowed"
          } text-white p-2 rounded-md w-full`}
          onClick={handleBooking}
          disabled={!isAvailable}
        >
          Book Now
        </button>
      </div>
    </div>
  );
};

export default HotelModal;
