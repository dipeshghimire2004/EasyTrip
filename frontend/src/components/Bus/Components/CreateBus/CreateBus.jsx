import React, { useState } from 'react';
import api from '../../../API/Api';

export default function CreateBus() {
  const [form, setForm] = useState({
    name: '',
    busType: '',
    totalSeats: '',
    verifiedDocumentImage: null
  });
  const [loading, setLoading] = useState(false);

  const handleChange = e => {
    if (e.target.name === 'verifiedDocumentImage') {
      setForm({ ...form, verifiedDocumentImage: e.target.files[0] });
    } else {
      setForm({ ...form, [e.target.name]: e.target.value });
    }
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);

    const formData = new FormData();
    formData.append('name', form.name);
    formData.append('busType', form.busType); // Ensure this matches your busType format, e.g., "AC_SLEEPER"
    formData.append('totalSeats', form.totalSeats);
    formData.append('verifiedDocumentImage', form.verifiedDocumentImage);

    try {
      await api.post('/api/bus', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      alert('Bus successfully created!');
      setForm({
        name: '',
        busType: '',
        totalSeats: '',
        verifiedDocumentImage: null
      });
    } catch (err) {
      console.error(err);
      alert('Creation failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-lg mx-auto p-6 bg-white rounded-2xl shadow-lg">
      <h2 className="text-2xl font-semibold mb-4">Create New Bus</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          name="name"
          value={form.name}
          onChange={handleChange}
          placeholder="Bus Name"
          className="w-full p-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <input
          name="busType"
          value={form.busType}
          onChange={handleChange}
          placeholder="Bus Type (e.g., AC_SLEEPER)"
          className="w-full p-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <input
          name="totalSeats"
          type="number"
          value={form.totalSeats}
          onChange={handleChange}
          placeholder="Total Seats"
          className="w-full p-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <input
          name="verifiedDocumentImage"
          type="file"
          onChange={handleChange}
          className="w-full p-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <button
          type="submit"
          disabled={loading}
          className="w-full py-3 bg-blue-600 text-white font-medium rounded-lg hover:bg-blue-700 disabled:opacity-50"
        >
          {loading ? 'Creating...' : 'Create Bus'}
        </button>
      </form>
    </div>
  );
}
