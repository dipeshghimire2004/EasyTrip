import React from "react";
import HotelCard from "./HotelCard";

const HotelList = ({ hotels, onViewDetails }) => {
  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
      {hotels.length ? (
        hotels.map((hotel, index) => (
          <HotelCard
            key={index}
            hotel={hotel}
            onViewDetails={(hotel) => onViewDetails(hotel)}
          />
        ))
      ) : (
        <p className="text-center col-span-3 text-3xl font-bold">
          No result found
        </p>
      )}
    </div>
  );
};

export default HotelList;
