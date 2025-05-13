import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "../../API/Api";
import Layout from "../../Layout/Layout";

const FindRoom = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const { guesthouseId, checkinDate, checkoutDate } = state || {};

  const [allRooms, setAllRooms] = useState([]);
  const [showAvailableOnly, setShowAvailableOnly] = useState(true);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!guesthouseId || !checkinDate || !checkoutDate) {
      console.error("Missing guesthouseId, checkinDate, or checkoutDate");
      return;
    }

    const fetchRooms = async () => {
      try {
        const res = await api.get(`/api/guesthouses/${guesthouseId}/rooms`, {
          params: {
            checkInDate: checkinDate,
            checkOutDate: checkoutDate,
          },
        });
        setAllRooms(res.data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchRooms();
  }, [guesthouseId, checkinDate, checkoutDate]);

  const filteredRooms = showAvailableOnly
    ? allRooms.filter((room) => room.available)
    : allRooms;

  const handleBookNow = (room) => {
    navigate("/CLIENT/payment", {
        state: {
        startDate: checkinDate,
        endDate: checkoutDate,
        pricePerNight: room.pricePerNight,
        guesthousesid: guesthouseId,
        roomId: room.id,  // Pass room ID
        },
    });
  };


  if (loading) return <p>Loading rooms...</p>;
  if (error) return <p className="text-red-500">Error: {error.message}</p>;

  return (
    <Layout>
      <div className="p-4">
        <div className="flex justify-between items-center mb-4">
          <h2 className="font-bold text-2xl">Rooms</h2>
          <button
            onClick={() => setShowAvailableOnly(!showAvailableOnly)}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
          >
            {showAvailableOnly ? "Show All Rooms" : "Show Only Available"}
          </button>
        </div>

        {filteredRooms.length ? (
          filteredRooms.map((room, index) => (
            <div
              key={room.id}
              className={`border p-4 rounded-md shadow-sm mb-4 flex items-start gap-4 ${
                !room.available ? "opacity-50" : ""
              }`}
            >
              <img
                src={`/room${(index % 4) + 1}.jpg`}
                alt={`Room ${room.roomNumber}`}
                className="w-40 h-28 object-cover rounded"
              />
              <div className="flex-1">
                <h3 className="text-lg font-bold">Room #{room.roomNumber}</h3>
                <p className="text-sm">Type: {room.roomType}</p>
                <p className="text-sm">Capacity: {room.capacity} person(s)</p>
                <p className="text-sm">Price: ${room.pricePerNight}</p>
                {!room.available && (
                  <p className="text-red-500 font-semibold mt-1">Not Available</p>
                )}
              </div>
              {room.available && (
                <button
                  onClick={() => handleBookNow(room)}
                  className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600 transition"
                >
                  Book Now
                </button>
              )}
            </div>
          ))
        ) : (
          <p>No rooms found for the selected criteria.</p>
        )}
      </div>
    </Layout>
  );
};

export default FindRoom;
