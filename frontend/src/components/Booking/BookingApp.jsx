import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import FilterPanel from "./FilterPanel";
import HotelList from "./HotelList";
import HotelModal from "./HotelModal";
import Layout from "../Layout/Layout";

// Static Data for Hotels and Filter Options
const hotels = [
  {
    name: "Cozy Guesthouse",
    price: 80,
    roomType: "Single",
    location: "Kathmandu",
    rating: 4.2,
    amenities: ["Wi-Fi", "Breakfast"],
  },
  {
    name: "Mountain View Inn",
    price: 120,
    roomType: "Double",
    location: "Pokhara",
    rating: 3.8,
    amenities: ["Wi-Fi", "Pool"],
  },
  {
    name: "Modern Guesthouse",
    price: 95,
    roomType: "Double",
    location: "Lalitpur",
    rating: 4.5,
    amenities: ["Wi-Fi", "Breakfast", "Gym"],
  },
  {
    name: "Heritage Guesthouse",
    price: 70,
    roomType: "Single",
    location: "Kathmandu",
    rating: 4.0,
    amenities: ["Wi-Fi", "Local Tours"],
  },
  {
    name: "Garden Retreat",
    price: 110,
    roomType: "Suite",
    location: "Pokhara",
    rating: 4.3,
    amenities: ["Wi-Fi", "Breakfast", "Garden"],
  },
  {
    name: "Spacious Rooms Inn",
    price: 130,
    roomType: "Suite",
    location: "Lalitpur",
    rating: 4.1,
    amenities: ["Wi-Fi", "Breakfast", "Parking"],
  },
];

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

  // Load cached filters if available
  const cachedFilters =
    JSON.parse(localStorage.getItem("bookingAppFilters")) || {};

  // Filter state variables
  const [searchTerm, setSearchTerm] = useState(
    cachedFilters.searchTerm || ""
  );
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

  // State for modal and availability
  const [selectedHotel, setSelectedHotel] = useState(null);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [isAvailable, setIsAvailable] = useState(false);
  const [availabilityChecked, setAvailabilityChecked] = useState(false);

  // Cache filters in localStorage on change
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

  // Filtering logic for hotels
  const filteredHotels = hotels.filter((hotel) => {
    if (
      searchTerm &&
      !hotel.name.toLowerCase().includes(searchTerm.toLowerCase())
    ) {
      return false;
    }
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
    if (selectedRoom !== "All ( Room )" && hotel.roomType !== selectedRoom) {
      return false;
    }
    if (selectedLocation !== "All ( Location )") {
      if (selectedLocation === "Custom") {
        if (
          customLocation &&
          !hotel.location.toLowerCase().includes(customLocation.toLowerCase())
        )
          return false;
      } else if (hotel.location !== selectedLocation) {
        return false;
      }
    }
    if (selectedRating !== "All ( Rating )") {
      const minRating = parseInt(selectedRating[0]);
      if (hotel.rating < minRating) return false;
    }
    if (selectedAmenities.length > 0) {
      const hasAmenities = selectedAmenities.every((amenity) =>
        hotel.amenities.includes(amenity)
      );
      if (!hasAmenities) return false;
    }
    return true;
  });

  // Close modal and reset availability
  const closeModal = () => {
    setSelectedHotel(null);
    setStartDate("");
    setEndDate("");
    setIsAvailable(false);
    setAvailabilityChecked(false);
  };

  // Check availability based on dates
  const checkAvailability = () => {
    if (startDate && endDate && new Date(endDate) > new Date(startDate)) {
      setIsAvailable(true);
    } else {
      setIsAvailable(false);
    }
    setAvailabilityChecked(true);
  };

  // Proceed to booking if available
  const handleBooking = () => {
    if (isAvailable) {
      navigate("/CLIENT/payment");
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

          <HotelList hotels={filteredHotels} onViewDetails={setSelectedHotel} />

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
