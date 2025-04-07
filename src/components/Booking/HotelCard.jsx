import React from "react";

const HotelCard = ({ hotel, onViewDetails }) => {
  return (
    <div className="border p-4 rounded-md">
      <div className="bg-gray-300 h-32 mb-2"></div>
      <h3 className="font-bold">{hotel.name}</h3>
      <p>${hotel.price} per night</p>
      <p className="text-sm">Room: {hotel.roomType}</p>
      <p className="text-sm">Location: {hotel.location}</p>
      <p className="text-sm">Rating: {hotel.rating} Stars</p>
      <p className="text-sm">Amenities: {hotel.amenities.join(", ")}</p>
      <button
        className="bg-green-500 text-white p-2 mt-2 rounded-md"
        onClick={() => onViewDetails(hotel)}
      >
        View Details
      </button>
    </div>
  );
};

export default HotelCard;
