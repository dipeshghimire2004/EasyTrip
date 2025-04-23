import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import FilterPanel from "./FilterPanel";
import HotelList from "./HotelList";
import HotelModal from "./HotelModal";
import Layout from "../Layout/Layout";
import api from "../API/Api"; // custom axios instance

const priceOptions = [
  "All ( Price )",
  "Under $100",
  "$100 - $150",
  "Above $150",
  "Custom",
];
const roomOptions = ["All ( Room )", "Single", "Double", "Suite"];
const amenityOptions = [
  "Wi-Fi",
  "Breakfast",
  "Pool",
  "Gym",
  "Local Tours",
  "Garden",
  "Parking",
];
const locationOptions = [
  "All ( Location )",
  "Kathmandu",
  "Pokhara",
  "Lalitpur",
  "Custom",
];
const ratingOptions = [
  "All ( Rating )",
  "1+ Stars",
  "2+ Stars",
  "3+ Stars",
  "4+ Stars",
  "5 Stars",
];

export default function BookingApp() {
  const navigate = useNavigate();

  const cachedFilters =
    JSON.parse(localStorage.getItem("bookingAppFilters")) || {};

  const [searchTerm, setSearchTerm] = useState(cachedFilters.searchTerm || "");
  const [selectedPrice, setSelectedPrice] = useState(
    cachedFilters.selectedPrice || "All ( Price )"
  );
  const [customMinPrice, setCustomMinPrice] = useState(
    cachedFilters.customMinPrice || ""
  );
  const [customMaxPrice, setCustomMaxPrice] = useState(
    cachedFilters.customMaxPrice || ""
  );
  const [selectedRoom, setSelectedRoom] = useState(
    cachedFilters.selectedRoom || "All ( Room )"
  );
  const [selectedLocation, setSelectedLocation] = useState(
    cachedFilters.selectedLocation || "All ( Location )"
  );
  const [customLocation, setCustomLocation] = useState(
    cachedFilters.customLocation || ""
  );
  const [selectedRating, setSelectedRating] = useState(
    cachedFilters.selectedRating || "All ( Rating )"
  );
  const [selectedAmenities, setSelectedAmenities] = useState(
    cachedFilters.selectedAmenities || []
  );

  const [selectedHotel, setSelectedHotel] = useState(null);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [isAvailable, setIsAvailable] = useState(false);
  const [availabilityChecked, setAvailabilityChecked] = useState(false);

  const [guesthouses, setGuesthouses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch guesthouses with Axios
  useEffect(() => {
    const fetchGuesthouses = async () => {
      try {
        const response = await api.get("/api/guesthouses/search");
        setGuesthouses(response.data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchGuesthouses();
  }, []);

  // Map guesthouses to hotels with necessary data
  const hotels = guesthouses
    .filter((item) => item.status === "APPROVED")
    .map((item) => ({
      name: item.name,
      price: item.pricePerNight,
      roomType: item.roomType || "Double",
      location: item.location,
      rating: item.rating || 4.0,
      amenities: Array.isArray(item.amenities)
        ? item.amenities.map((a) => a.trim())
        : [],
      id: item.id,
      raw: item,
    }));

  useEffect(() => {
    const filters = {
      searchTerm,
      selectedPrice,
      customMinPrice,
      customMaxPrice,
      selectedRoom,
      selectedLocation,
      customLocation,
      selectedRating,
      selectedAmenities,
    };
    localStorage.setItem("bookingAppFilters", JSON.stringify(filters));
  }, [
    searchTerm,
    selectedPrice,
    customMinPrice,
    customMaxPrice,
    selectedRoom,
    selectedLocation,
    customLocation,
    selectedRating,
    selectedAmenities,
  ]);

  const filteredHotels = hotels.filter((hotel) => {
    if (
      searchTerm &&
      !hotel.name.toLowerCase().includes(searchTerm.toLowerCase())
    )
      return false;

    if (selectedPrice !== "All ( Price )") {
      if (selectedPrice === "Under $100" && hotel.price >= 100) return false;
      if (
        selectedPrice === "$100 - $150" &&
        (hotel.price < 100 || hotel.price > 150)
      )
        return false;
      if (selectedPrice === "Above $150" && hotel.price <= 150) return false;
      if (selectedPrice === "Custom") {
        const min = customMinPrice ? parseFloat(customMinPrice) : -Infinity;
        const max = customMaxPrice ? parseFloat(customMaxPrice) : Infinity;
        if (hotel.price < min || hotel.price > max) return false;
      }
    }

    if (selectedRoom !== "All ( Room )" && hotel.roomType !== selectedRoom)
      return false;

    if (selectedLocation !== "All ( Location )") {
      if (selectedLocation === "Custom") {
        if (
          customLocation &&
          !hotel.location.toLowerCase().includes(customLocation.toLowerCase())
        )
          return false;
      } else if (hotel.location !== selectedLocation) return false;
    }

    if (selectedRating !== "All ( Rating )") {
      const minRating = parseInt(selectedRating[0]);
      if (hotel.rating < minRating) return false;
    }

    if (selectedAmenities.length > 0) {
      const hasAll = selectedAmenities.every((a) =>
        hotel.amenities.includes(a)
      );
      if (!hasAll) return false;
    }

    return true;
  });

  const closeModal = () => {
    setSelectedHotel(null);
    setStartDate("");
    setEndDate("");
    setIsAvailable(false);
    setAvailabilityChecked(false);
  };

  const checkAvailability = () => {
    if (startDate && endDate && new Date(endDate) > new Date(startDate)) {
      setIsAvailable(true);
    } else {
      setIsAvailable(false);
    }
    setAvailabilityChecked(true);
  };

  const handleBooking = () => {
    if (isAvailable) {
      navigate("/CLIENT/payment", {
        state: {
          startDate,
          endDate,
          pricePerNight: selectedHotel.price,
          guesthousesid: selectedHotel.id,
        },
      });
    }
  };

  return (
    <Layout>
      <div className="p-4">
        <FilterPanel
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          selectedPrice={selectedPrice}
          setSelectedPrice={setSelectedPrice}
          customMinPrice={customMinPrice}
          setCustomMinPrice={setCustomMinPrice}
          customMaxPrice={customMaxPrice}
          setCustomMaxPrice={setCustomMaxPrice}
          selectedRoom={selectedRoom}
          setSelectedRoom={setSelectedRoom}
          selectedLocation={selectedLocation}
          setSelectedLocation={setSelectedLocation}
          customLocation={customLocation}
          setCustomLocation={setCustomLocation}
          selectedRating={selectedRating}
          setSelectedRating={setSelectedRating}
          selectedAmenities={selectedAmenities}
          setSelectedAmenities={setSelectedAmenities}
          priceOptions={priceOptions}
          roomOptions={roomOptions}
          locationOptions={locationOptions}
          ratingOptions={ratingOptions}
          amenityOptions={amenityOptions}
        />

        {loading && <p>Loading hotels...</p>}
        {error && (
          <p className="text-red-500">
            Failed to load guesthouses: {error.message}
          </p>
        )}
        {!loading && !error && (
          <HotelList hotels={filteredHotels} onViewDetails={setSelectedHotel} />
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
            handleBooking={handleBooking}
          />
        )}
      </div>
    </Layout>
  );
}
