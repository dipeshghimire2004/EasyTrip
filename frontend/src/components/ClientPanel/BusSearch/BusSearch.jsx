import React, { useState } from 'react';
import Layout from '../../Layout/Layout';

const busesData = [
  {
    id: 1,
    name: 'City Shuttle Pro',
    type: 'Mini Bus',
    seats: 25,
    location: 'Los Angeles',
    available: true,
    price: 250,
    rating: null,
    availableDate: '2025-05-01',
    img: '',
  },
  {
    id: 2,
    name: 'Executive Transit',
    type: 'Coach',
    seats: 35,
    location: 'Chicago',
    available: false,
    price: 350,
    rating: 4.9,
    availableDate: '2025-05-03',
    img: '',
  },
  {
    id: 3,
    name: 'Luxury Coach XL',
    type: 'Coach',
    seats: 45,
    location: 'New York',
    available: true,
    price: 450,
    rating: 4.8,
    availableDate: '2025-05-01',
    img: '',
  },
];

export default function BusSearch() {
  const [search, setSearch] = useState('');
  const [sortBy, setSortBy] = useState('price');
  const [searchDate, setSearchDate] = useState('');

  const filtered = busesData
    .filter(bus =>
      bus.name.toLowerCase().includes(search.toLowerCase()) &&
      (!searchDate || bus.availableDate === searchDate)
    )
    .sort((a, b) =>
      sortBy === 'price' ? a.price - b.price : b.rating - a.rating
    );

  return (
    <Layout>
      <div className="min-h-screen bg-gray-100 p-6">
        {/* Header */}
        <div className="mb-6">
          <h1 className="text-2xl font-semibold">Bus Search</h1>
        </div>

        {/* Controls */}
        <div className="flex flex-col md:flex-row gap-4 mb-6">
          <input
            type="text"
            placeholder="Search buses..."
            value={search}
            onChange={e => setSearch(e.target.value)}
            className="flex-1 px-4 py-2 rounded border border-gray-300 focus:outline-none focus:ring"
          />
          <input
            type="date"
            value={searchDate}
            onChange={e => setSearchDate(e.target.value)}
            className="w-full md:w-48 px-4 py-2 rounded border border-gray-300 focus:outline-none"
          />
          <select
            value={sortBy}
            onChange={e => setSortBy(e.target.value)}
            className="w-full md:w-48 px-4 py-2 rounded bg-gray-200 focus:outline-none"
          >
            <option value="price">Sort by Price</option>
            <option value="rating">Sort by Rating</option>
          </select>
        </div>

        {/* Results Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {filtered.map(bus => (
            <div key={bus.id} className="bg-white rounded-lg shadow overflow-hidden">
              <img src={bus.img} alt={bus.name} className="w-full h-48 object-cover" />
              <div className="p-4">
                <div className="flex justify-between items-start">
                  <div>
                    <h2 className="text-lg font-semibold">{bus.name}</h2>
                    <p className="text-gray-600 text-sm">
                      {bus.type} &bull; {bus.seats} seats
                    </p>
                    <p className="text-gray-600 text-sm">
                      {bus.location} &bull;{' '}
                      <span className={bus.available ? 'text-green-600' : 'text-red-600'}>
                        {bus.available ? 'Available' : 'Unavailable'}
                      </span>
                    </p>
                    {bus.rating && (
                      <p className="mt-2 font-medium">{bus.rating} rating</p>
                    )}
                    <p className="text-sm text-gray-500 mt-1">Available on: {bus.availableDate}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-lg font-semibold">${bus.price}/day</p>
                  </div>
                </div>
                <div className="mt-4 text-right">
                  <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">
                    View Details
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </Layout>
  );
}
