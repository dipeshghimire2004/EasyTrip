import React, { useState, useEffect } from 'react';
import api from '../../../API/Api';

export default function UpdateBus({ busId }) {
  const [form, setForm] = useState({ busNumber: '', capacity: '', features: '', route: '' });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    api.get(`/api/bus/${busId}`).then(res => {
      const { busNumber, capacity, features, route } = res.data;
      setForm({ busNumber, capacity, features: features.join(','), route });
    });
  }, [busId]);

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    try {
      await axios.put(`/api/bus/${busId}`, { ...form, features: form.features.split(',').map(f => f.trim()) });
      alert('Bus updated!');
    } catch {
      alert('Update failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-lg mx-auto p-6 bg-white rounded-2xl shadow-lg">
      <h2 className="text-2xl font-semibold mb-4">Update Bus</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input name="busNumber" value={form.busNumber} onChange={handleChange} placeholder="Bus Number" className="w-full p-3 border rounded-lg focus:ring-2 focus:ring-green-400" />
        <input name="capacity" type="number" value={form.capacity} onChange={handleChange} placeholder="Capacity" className="w-full p-3 border rounded-lg focus:ring-2 focus:ring-green-400" />
        <input name="features" value={form.features} onChange={handleChange} placeholder="Features" className="w-full p-3 border rounded-lg focus:ring-2 focus:ring-green-400" />
        <input name="route" value={form.route} onChange={handleChange} placeholder="Route" className="w-full p-3 border rounded-lg focus:ring-2 focus:ring-green-400" />
        <button type="submit" disabled={loading} className="w-full py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 disabled:opacity-50">{loading ? 'Updating...' : 'Update Bus'}</button>
      </form>
    </div>
  );
}