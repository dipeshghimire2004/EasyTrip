import React, { useState } from "react";

export default function Payment() {
  const [isBooked, setIsBooked] = useState(false);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const today = new Date().toISOString().split("T")[0]; // Get today's date in YYYY-MM-DD format

  const isButtonDisabled =
    !startDate ||
    !endDate ||
    new Date(startDate) < new Date(today) || // Prevents past dates
    new Date(startDate) >= new Date(endDate); // Prevents same start and end date

  return (
    <div className="p-4 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-4">Booking & Payment</h1>
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
        <p>Base Price: $80</p>
        <p>Total: $80</p>
      </div>
      <div className="mb-4">
        <h2 className="font-bold">Payment Options:</h2>
        <p>Cash</p>
      </div>
      <button
        className={`p-2 rounded-md w-full ${isButtonDisabled ? "bg-gray-400 cursor-not-allowed" : "bg-green-500 text-white"}`}
        onClick={() => setIsBooked(true)}
        disabled={isButtonDisabled}
      >
        Confirm Booking
      </button>
      {isBooked && (
        <p className="mt-2 text-green-600">
          Booking Confirmed! Thank you for choosing EasyTrip.
        </p>
      )}
    </div>
  );
}
