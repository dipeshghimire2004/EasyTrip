import React, { useState } from 'react';
import api from '../../../API/Api';

export default function CreateBus() {
  const [form, setForm] = useState({
    name: '',
    ownerName: '',
    ownerPhone: '',
    busType: '',
    totalSeats: '',
    source: '',
    destination: '',
    departureTime: '',
    arrivalTime: '',
    verifiedDocument: null
  });

  const [loading, setLoading] = useState(false);

  const handleChange = e => {
    const { name, value, files } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: name === 'verifiedDocument' ? files[0] : value
    }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);

    const formData = new FormData();
    Object.entries(form).forEach(([key, value]) => {
      formData.append(key, value);
    });

    try {
      const response = await api.post('/api/buses/register', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      console.log('Server Response:', response); // 👈 Print the response here
      alert('Bus successfully created!');
      setForm({
        name: '',
        ownerName: '',
        ownerPhone: '',
        busType: '',
        totalSeats: '',
        source: '',
        destination: '',
        departureTime: '',
        arrivalTime: '',
        verifiedDocument: null
      });
    } catch (err) {
      console.error('Error:', err); // 👈 Keep this for debugging
      alert('Creation failed');
    } finally {
      setLoading(false);
    }
  };


  return (
    <div className="max-w-lg mx-auto p-6 bg-white rounded-2xl shadow-lg">
      <h2 className="text-2xl font-semibold mb-4">Create New Bus</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        {[
          { name: 'name', placeholder: 'Bus Name' },
          { name: 'ownerName', placeholder: 'Owner Name' },
          { name: 'ownerPhone', placeholder: 'Owner Phone' },
          { name: 'busType', placeholder: 'Bus Type (AC, NON_AC, SLEEPER)' },
          { name: 'totalSeats', placeholder: 'Total Seats', type: 'number' },
          { name: 'source', placeholder: 'Source Location' },
          { name: 'destination', placeholder: 'Destination Location' },
          { name: 'departureTime', placeholder: 'Departure Time', type: 'datetime-local' },
          { name: 'arrivalTime', placeholder: 'Arrival Time', type: 'datetime-local' },
        ].map(({ name, placeholder, type = 'text' }) => (
          <input
            key={name}
            name={name}
            type={type}
            value={form[name]}
            onChange={handleChange}
            placeholder={placeholder}
            className="w-full p-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        ))}
        <input
          name="verifiedDocument"
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
