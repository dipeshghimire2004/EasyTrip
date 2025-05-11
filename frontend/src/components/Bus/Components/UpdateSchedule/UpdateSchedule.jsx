import React, { useState } from 'react';
import api from '../../../API/Api';

export default function UpdateSchedule({ scheduleId }) {
  const [form, setForm] = useState({ departureTime: '', arrivalTime: '', price: '' });

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async () => {
    await api.put(`/api/bus/schedule/${scheduleId}`, form);
    alert('Schedule Updated');
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg space-y-4">
      <h2 className="text-2xl font-bold text-center text-gray-800">Update Schedule</h2>

      <input
        name="departureTime"
        placeholder="Departure Time (ISO)"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-400"
      />
      <input
        name="arrivalTime"
        placeholder="Arrival Time (ISO)"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-400"
      />
      <input
        name="price"
        placeholder="Price"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-400"
      />

      <button
        onClick={handleSubmit}
        className="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700 transition"
      >
        Update
      </button>
    </div>
  );
}
