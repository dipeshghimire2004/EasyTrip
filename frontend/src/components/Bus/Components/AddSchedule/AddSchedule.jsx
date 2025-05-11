import React, { useState, useEffect } from 'react';
import api from '../../../API/Api';

export default function AddSchedule() {
  const [form, setForm] = useState({
    busId: '',
    departureTime: '',
    arrivalTime: '',
    departureLocation: '',
    arrivalLocation: '',
    price: '',
  });

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async () => {
    await api.post('/api/bus/schedule', form);
    alert('Schedule Created');
  };

  return (
    <div className="max-w-md mx-auto p-6 bg-white shadow-md rounded-lg mt-10 space-y-4">
      <h2 className="text-2xl font-semibold text-center text-gray-800">Add Schedule</h2>
      
      <input
        name="busId"
        placeholder="Bus ID"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="departureTime"
        placeholder="Departure Time (ISO)"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="arrivalTime"
        placeholder="Arrival Time (ISO)"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="departureLocation"
        placeholder="Departure Location"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="arrivalLocation"
        placeholder="Arrival Location"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="price"
        placeholder="Price"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />

      <button
        onClick={handleSubmit}
        className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition"
      >
        Submit
      </button>
    </div>
  );
}
