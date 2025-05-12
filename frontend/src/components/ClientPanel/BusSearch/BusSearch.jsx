import React, { useState } from 'react';
import Layout from '../../Layout/Layout';
import api from '../../API/Api';

export default function BusSearch() {
  const [source, setSource] = useState('');
  const [destination, setDestination] = useState('');
  const [buses, setBuses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Modal state
  const [selectedBus, setSelectedBus] = useState(null);
  const [showModal, setShowModal] = useState(false);
  // Track bookings per busId: { [busId]: { id, seatsBooked, ... } }
  const [bookings, setBookings] = useState({});

  const handleSearch = async () => {
    const trimmedSource = source.trim();
    const trimmedDestination = destination.trim();
    if (!trimmedSource || !trimmedDestination) return;

    setLoading(true);
    setError(null);

    try {
      const res = await api.get(`api/buses/search`, {
        params: { source: trimmedSource, destination: trimmedDestination }
      });
      setBuses(res.data);
    } catch (e) {
      setError(e.message);
      setBuses([]);
    } finally {
      setLoading(false);
    }
  };

  const openModal = (bus) => {
    setSelectedBus(bus);
    setShowModal(true);
  };
  const closeModal = () => {
    setShowModal(false);
    setSelectedBus(null);
  };

  const handleBooking = async (busId, seats) => {
    try {
      const res = await api.post('api/buses/book', {
        busId,
        seatsBooked: seats,
      });
      const booking = res.data;
      setBookings(prev => ({ ...prev, [busId]: booking }));
      alert('Successfully booked!');
    } catch (e) {
      console.error(e);
      alert('Booking failed: ' + e.message);
    }
  };

  const handleCancel = async (busId) => {
    const booking = bookings[busId];
    if (!booking) return;
    try {
      await api.put(`api/buses/bookings/${booking.id}/cancel`);
      setBookings(prev => {
        const copy = { ...prev };
        delete copy[busId];
        return copy;
      });
      alert('Booking cancelled.');
    } catch (e) {
      console.error(e);
      alert('Cancel failed: ' + e.message);
    }
  };

  return (
    <Layout>
      <div className="min-h-screen bg-gray-100 p-6">
        <h1 className="text-2xl font-semibold mb-6">Bus Search</h1>

        <div className="flex flex-col md:flex-row gap-4 mb-6">
          <input
            className="flex-1 px-4 py-2 rounded border border-gray-300 focus:outline-none focus:ring"
            placeholder="Source"
            value={source}
            onChange={e => setSource(e.target.value)}
          />
          <input
            className="flex-1 px-4 py-2 rounded border border-gray-300 focus:outline-none focus:ring"
            placeholder="Destination"
            value={destination}
            onChange={e => setDestination(e.target.value)}
          />
          <button
            onClick={handleSearch}
            disabled={loading}
            className="w-full md:w-48 px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700 transition disabled:opacity-50"
          >
            {loading ? 'Searching…' : 'Search'}
          </button>
        </div>

        {error && <div className="mb-4 text-red-600">{error}</div>}

        {buses.length === 0 && !loading ? (
          <p className="text-gray-600">No buses found for these locations.</p>
        ) : (
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {buses.map((bus, idx) => {
              const imgNum = (idx % 4) + 1;
              const imgPath = `/bus${imgNum}.jpg`;
              return (
                <div key={bus.id} className="bg-white rounded-lg shadow overflow-hidden">
                  <div
                    className="relative h-48"
                    style={{
                      backgroundImage: `url(${imgPath})`,
                      backgroundSize: 'cover',
                      backgroundPosition: 'center'
                    }}
                  >
                    <img
                      src={imgPath}
                      alt={bus.name}
                      className="absolute inset-0 w-full h-full object-contain"
                    />
                  </div>
                  <div className="p-4">
                    <h2 className="text-lg font-semibold">{bus.name}</h2>
                    <p className="text-gray-600">{bus.source} → {bus.destination}</p>
                    <div className="mt-4 text-right">
                      <button
                        onClick={() => openModal(bus)}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                      >
                        View Details
                      </button>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        )}

        {/* Modal Overlay */}
        {showModal && selectedBus && (
          <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
            <div className="bg-white rounded-lg w-11/12 md:w-2/3 lg:w-1/2 overflow-auto max-h-[90vh]">
              <div className="p-6">
                <h2 className="text-xl font-bold mb-4">{selectedBus.name}</h2>
                <p><strong>Type:</strong> {selectedBus.busType}</p>
                <p><strong>Seats:</strong> {selectedBus.totalSeats}</p>
                <p><strong>Route:</strong> {selectedBus.source} → {selectedBus.destination}</p>
                <p><strong>Departs:</strong> {new Date(selectedBus.departureTime).toLocaleString()}</p>
                <p><strong>Arrives:</strong> {new Date(selectedBus.arrivalTime).toLocaleString()}</p>
                <p><strong>Fare:</strong> ${selectedBus.farePerSeat}</p>
                <p className={`mt-2 ${selectedBus.status === 'APPROVED' ? 'text-green-600' : 'text-red-600'}`}>
                  {selectedBus.status}
                </p>
                <p className="mt-4 text-gray-600"><strong>Operator ID:</strong> {selectedBus.operatorId}</p>
                <p className="text-gray-600"><strong>Owner:</strong> {selectedBus.ownerName}</p>
                <p className="text-gray-600"><strong>Contact:</strong> {selectedBus.ownerPhone}</p>

                <div className="mt-6 flex justify-end items-center gap-4">
                  <button
                    onClick={closeModal}
                    className="px-4 py-2 rounded border border-gray-300 hover:bg-gray-100 transition"
                  >
                    Close
                  </button>

                  {bookings[selectedBus.id] ? (
                    <button
                      onClick={() => handleCancel(selectedBus.id)}
                      className="px-4 py-2 rounded bg-red-600 text-white hover:bg-red-700 transition"
                    >
                      Cancel Booking
                    </button>
                  ) : (
                    <button
                      onClick={() => handleBooking(selectedBus.id, 1)}
                      className="px-4 py-2 rounded bg-green-600 text-white hover:bg-green-700 transition"
                    >
                      Book Now
                    </button>
                  )}
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}
