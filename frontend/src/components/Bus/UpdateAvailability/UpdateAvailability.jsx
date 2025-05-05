import React, { useState } from "react";

const UpdateAvailability = () => {
  const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  const [selectedDays, setSelectedDays] = useState([]);
  const [startTime, setStartTime] = useState("09:00");
  const [endTime, setEndTime] = useState("18:00");

  const toggleDay = (day) => {
    setSelectedDays((prev) =>
      prev.includes(day) ? prev.filter((d) => d !== day) : [...prev, day]
    );
  };

  const handleSave = () => {
    alert(`Saved! Days: ${selectedDays.join(", ")}, Time: ${startTime} - ${endTime}`);
  };

  return (
    <div className="max-w-lg mx-auto mt-10 p-6 bg-white rounded-2xl shadow-xl">
      <h2 className="text-2xl font-bold text-gray-800 mb-2">Update Bus Availability</h2>
      <p className="text-gray-600 mb-6">
        Modify the availability for your bus by adjusting the days and hours when it is operating.
      </p>

      <div className="mb-6">
        <h3 className="text-lg font-semibold text-gray-700 mb-2">Bus Days</h3>
        <div className="flex flex-wrap gap-2">
          {days.map((day) => (
            <button
              key={day}
              onClick={() => toggleDay(day)}
              className={`px-4 py-2 rounded-lg border transition-colors duration-200 font-medium ${
                selectedDays.includes(day)
                  ? "bg-blue-600 text-white border-blue-600"
                  : "bg-gray-100 text-gray-800 border-gray-300 hover:bg-gray-200"
              }`}
            >
              {day}
            </button>
          ))}
        </div>
      </div>

      <div className="mb-6">
        <h3 className="text-lg font-semibold text-gray-700 mb-2">Time Range</h3>
        <div className="flex gap-4">
          <input
            type="time"
            value={startTime}
            onChange={(e) => setStartTime(e.target.value)}
            className="w-full px-4 py-2 border rounded-lg text-gray-700 shadow-sm"
          />
          <input
            type="time"
            value={endTime}
            onChange={(e) => setEndTime(e.target.value)}
            className="w-full px-4 py-2 border rounded-lg text-gray-700 shadow-sm"
          />
        </div>
      </div>

      <button
        onClick={handleSave}
        className="w-full py-3 bg-blue-600 text-white font-bold rounded-xl shadow-md hover:bg-blue-700 transition duration-300"
      >
        Save
      </button>
    </div>
  );
};

export default UpdateAvailability;
