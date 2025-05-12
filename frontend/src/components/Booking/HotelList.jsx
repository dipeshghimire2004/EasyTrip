// src/pages/HotelList.jsx
import React from "react";
import HotelCard from "./HotelCard";

const HotelList = ({ hotels, onViewDetails }) => (
  <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
    {hotels.length ? (
      hotels.map((hotel, index) => (
        <HotelCard
          key={hotel.id}
          hotel={hotel}
          index={index}
          onViewDetails={() => onViewDetails(hotel)}
        />
      ))
    ) : (
      <p className="text-center col-span-3 text-3xl font-bold">No results</p>
    )}
  </div>
);

export default HotelList;
