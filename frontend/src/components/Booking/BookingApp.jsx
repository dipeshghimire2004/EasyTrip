import React, { useState, useEffect } from "react";
import FilterPanel from "./FilterPanel";
import HotelList from "./HotelList";
import HotelModal from "./HotelModal";
import Layout from "../Layout/Layout";
import api from "../API/Api";

export default function BookingApp() {
  // Filter states
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedLocation, setSelectedLocation] = useState("All ( Location )");
  const [customLocation, setCustomLocation] = useState("");
  const [selectedRating, setSelectedRating] = useState("All ( Rating )");
  const [selectedAmenities, setSelectedAmenities] = useState([]);

  // Modal + date states
  const [selectedHotel, setSelectedHotel] = useState(null);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [isAvailable, setIsAvailable] = useState(false);
  const [availabilityChecked, setAvailabilityChecked] = useState(false);

  // Guesthouses data
  const [guesthouses, setGuesthouses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchGuesthouses = async () => {
      try {
        const res = await api.get("/api/guesthouses/search");
        setGuesthouses(res.data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };
    fetchGuesthouses();
  }, []);

  const hotels = guesthouses
    .filter((g) => g.status === "APPROVED")
    .map((g) => ({
      id: g.id,
      name: g.name,
      location: g.location,
      rating: g.rating || 4.0,
      description: g.description || "No description provided.",
      amenities: Array.isArray(g.amenities) ? g.amenities.map((a) => a.trim()) : [],
      image: g.verifiedDocument || "", // used as image src
    }));

  const filtered = hotels.filter((h) => {
    if (searchTerm && !h.name.toLowerCase().includes(searchTerm.toLowerCase()))
      return false;
    if (selectedLocation !== "All ( Location )") {
      if (selectedLocation === "Custom") {
        if (customLocation && !h.location.toLowerCase().includes(customLocation.toLowerCase()))
          return false;
      } else if (h.location !== selectedLocation) return false;
    }
    if (selectedAmenities.length) {
      if (!selectedAmenities.every((a) => h.amenities.includes(a))) return false;
    }
    return true;
  });


  const closeModal = () => {
    setSelectedHotel(null);
    setStartDate("");
    setEndDate("");
    setAvailabilityChecked(false);
    setIsAvailable(false);
  };

  const checkAvailability = () => {
    setIsAvailable(
      !!(startDate && endDate && new Date(endDate) > new Date(startDate))
    );
    setAvailabilityChecked(true);
  };

  return (
    <Layout>
      <div className="p-4">
        <FilterPanel
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          selectedLocation={selectedLocation}
          setSelectedLocation={setSelectedLocation}
          customLocation={customLocation}
          setCustomLocation={setCustomLocation}
          selectedRating={selectedRating}
          setSelectedRating={setSelectedRating}
          selectedAmenities={selectedAmenities}
          setSelectedAmenities={setSelectedAmenities}
        />

        {loading && <p>Loading guesthouses…</p>}
        {error && <p className="text-red-500">Error: {error.message}</p>}
        {!loading && !error && (
          <HotelList hotels={filtered} onViewDetails={setSelectedHotel} />
        )}

        {selectedHotel && (
          <HotelModal
            hotel={selectedHotel}
            closeModal={closeModal}
            startDate={startDate}
            setStartDate={setStartDate}
            endDate={endDate}
            setEndDate={setEndDate}
            checkAvailability={checkAvailability}
            availabilityChecked={availabilityChecked}
            isAvailable={isAvailable}
          />
        )}
      </div>
    </Layout>
  );
}
