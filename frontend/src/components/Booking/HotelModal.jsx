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
}) => {
  const navigate = useNavigate();
  const today = new Date().toISOString().split("T")[0];

  const handleFindRoom = () => {
    if (!isAvailable) return;

    // Navigate to the 'find-room' page and pass the required data
    navigate("/CLIENT/findRoom", {
      state: { guesthouseId: hotel.id, checkinDate: startDate, checkoutDate: endDate },
    });
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50 backdrop-blur-md">
      <div className="bg-white p-6 rounded-md max-w-lg w-full relative">
        <button
          className="absolute top-2 right-2 bg-red-500 text-white p-1 rounded-md"
          onClick={closeModal}
        >
          Close
        </button>

        <h2 className="font-bold text-xl mb-2">{hotel.name}</h2>
        <p className="mb-4">A lovely guesthouse in {hotel.location}.</p>
        <p className="mb-4">Amenities: {hotel.amenities.join(", ")}</p>

        <div className="mb-4">
          <p className="font-semibold mb-2">Select Dates:</p>
          <div className="flex gap-2">
            <input
              type="date"
              className="border p-2 rounded-md"
              value={startDate}
              min={today}
              onChange={(e) => setStartDate(e.target.value)}
            />
            <input
              type="date"
              className="border p-2 rounded-md"
              value={endDate}
              min={startDate || today}
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
                ? "Room is available!"
                : "Room NOT available; adjust dates."}
            </p>
          )}
        </div>

        <button
          className={`${
            isAvailable ? "bg-blue-500" : "bg-gray-400 cursor-not-allowed"
          } text-white p-2 rounded-md w-full`}
          onClick={handleFindRoom}
          disabled={!isAvailable}
        >
          Find Room
        </button>
      </div>
    </div>
  );
};

export default HotelModal;
