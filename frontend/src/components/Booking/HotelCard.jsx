// src/pages/HotelCard.jsx
import React from "react";

const HotelCard = ({ hotel, index, onViewDetails }) => {
  const imageIndex = (index % 4) + 1; // Cycles from hotel1.jpg to hotel4.jpg
  const imageUrl = `/hotel${imageIndex}.jpg`; // From /public folder

  return (
    <div className="border p-4 rounded-md shadow-sm">
      <div className="h-32 mb-2 overflow-hidden rounded">
        <img
          src={imageUrl}
          alt={hotel.name}
          className="w-full h-full object-cover"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src = "/default-hotel.jpg"; // Optional fallback
          }}
        />
      </div>
      <h3 className="font-bold text-lg">{hotel.name}</h3>
      <p className="mb-1">Location: {hotel.location}</p>
      <p className="mb-2 text-gray-600">{hotel.description}</p>

      {hotel.amenities
        .filter((a) => ["Wi-Fi", "Pool", "Gym", "Parking", "Breakfast"].includes(a))
        .map((amenity, i) => (
          <li key={i}>{amenity}</li>
      ))}

      <button
        className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
        onClick={onViewDetails}
      >
        View &amp; Book
      </button>
    </div>
  );
};

export default HotelCard;
