import React, { useState } from "react";

const BusBooking = () => {
  const [travelDate, setTravelDate] = useState("");
  const [destination, setDestination] = useState("");
  const [passengers, setPassengers] = useState("1 Passenger");

  const handleSubmit = (e) => {
    e.preventDefault();

    const unavailableDates = ["2025-04-25", "2025-05-01"];

    if (unavailableDates.includes(travelDate)) {
      alert("Fully Booked! Please select another date.");
      return;
    }

    console.log("Travel Date:", travelDate);
    console.log("Destination:", destination);
    console.log("Passengers:", passengers);
  };

  return (
    <div className="max-w-xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
      <form onSubmit={handleSubmit} className="flex flex-col">
        <div className="mb-4">
          <label className="block mb-2 font-bold text-sm">Travel Date</label>
          <input
            type="date"
            value={travelDate}
            onChange={(e) => setTravelDate(e.target.value)}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <div className="mb-4">
          <label className="block mb-2 font-bold text-sm">Destination</label>
          <input
            type="text"
            placeholder="Enter your destination"
            value={destination}
            onChange={(e) => setDestination(e.target.value)}
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <div className="mb-4">
          <label className="block mb-2 font-bold text-sm">Passengers</label>
          <select
            value={passengers}
            onChange={(e) => setPassengers(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400"
          >
            <option>1 Passenger</option>
            <option>2 Passengers</option>
            <option>3 Passengers</option>
            <option>4 Passengers</option>
          </select>
        </div>

        <div className="flex justify-end">
          <button
            type="submit"
            className="bg-blue-500 hover:bg-blue-600 text-white px-5 py-2 rounded-md transition duration-200"
          >
            Search Buses
          </button>
        </div>
      </form>
    </div>
  );
};

export default BusBooking;
