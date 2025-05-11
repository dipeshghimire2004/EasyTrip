import React, { useState } from "react";
import api from '../../../API/Api';

export default function SearchSchedule() {
  const [query, setQuery] = useState({ departureLocation: '', arrivalLocation: '', date: '' });
  const [results, setResults] = useState([]);

  const handleChange = e => setQuery({ ...query, [e.target.name]: e.target.value });

  const handleSubmit = async () => {
    const res = await api.post('/api/bus/schedule/search', query);
    setResults(res.data);
  };

  return (
    <div className="max-w-md mx-auto p-6 bg-white shadow-md rounded-lg mt-10 space-y-4">
      <h2 className="text-2xl font-semibold text-center text-gray-800">Search Schedule</h2>

      <input
        name="departureLocation"
        placeholder="From"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="arrivalLocation"
        placeholder="To"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <input
        name="date"
        placeholder="Date (YYYY-MM-DD)"
        onChange={handleChange}
        className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />

      <button
        onClick={handleSubmit}
        className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition"
      >
        Search
      </button>

      <ul className="list-disc pl-5 space-y-2 text-gray-700">
        {results.map((s, i) => (
          <li key={i} className="bg-gray-100 p-2 rounded">
            {JSON.stringify(s)}
          </li>
        ))}
      </ul>
    </div>
  );
}
