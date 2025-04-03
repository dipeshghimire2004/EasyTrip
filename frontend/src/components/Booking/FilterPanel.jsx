import React from "react";

const FilterPanel = ({
  // filter values and setters
  searchTerm,
  setSearchTerm,
  selectedPrice,
  setSelectedPrice,
  customMinPrice,
  setCustomMinPrice,
  customMaxPrice,
  setCustomMaxPrice,
  selectedRoom,
  setSelectedRoom,
  selectedLocation,
  setSelectedLocation,
  customLocation,
  setCustomLocation,
  selectedRating,
  setSelectedRating,
  selectedAmenities,
  setSelectedAmenities,
  // Options arrays
  priceOptions,
  roomOptions,
  locationOptions,
  ratingOptions,
  amenityOptions,
}) => {
  // Amenity change handler
  const handleAmenityChange = (e) => {
    const { value, checked } = e.target;
    if (checked) {
      setSelectedAmenities((prev) => [...prev, value]);
    } else {
      setSelectedAmenities((prev) =>
        prev.filter((amenity) => amenity !== value)
      );
    }
  };

  return (
    <div>
      {/* Search Input */}
      <div className="flex flex-col md:flex-row md:gap-2 mb-4">
        <input
          type="text"
          placeholder="Search by Hotel Name"
          className="border p-2 mb-2 md:mb-0 rounded-md"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />

        <select
          className="border p-2 mb-2 md:mb-0 rounded-md"
          value={selectedPrice}
          onChange={(e) => setSelectedPrice(e.target.value)}
        >
          {priceOptions.map((option, index) => (
            <option key={index} value={option}>
              {option}
            </option>
          ))}
        </select>

        <select
          className="border p-2 mb-2 md:mb-0 rounded-md"
          value={selectedRoom}
          onChange={(e) => setSelectedRoom(e.target.value)}
        >
          {roomOptions.map((option, index) => (
            <option key={index} value={option}>
              {option}
            </option>
          ))}
        </select>

        <select
          className="border p-2 mb-2 md:mb-0 rounded-md"
          value={selectedLocation}
          onChange={(e) => setSelectedLocation(e.target.value)}
        >
          {locationOptions.map((option, index) => (
            <option key={index} value={option}>
              {option}
            </option>
          ))}
        </select>

        <select
          className="border p-2 mb-2 md:mb-0 rounded-md"
          value={selectedRating}
          onChange={(e) => setSelectedRating(e.target.value)}
        >
          {ratingOptions.map((option, index) => (
            <option key={index} value={option}>
              {option}
            </option>
          ))}
        </select>
      </div>

      {/* Custom Price Input */}
      {selectedPrice === "Custom" && (
        <div className="flex gap-2 mb-4">
          <input
            type="number"
            placeholder="Min Price"
            className="border p-2 rounded-md"
            value={customMinPrice}
            onChange={(e) => setCustomMinPrice(e.target.value)}
          />
          <input
            type="number"
            placeholder="Max Price"
            className="border p-2 rounded-md"
            value={customMaxPrice}
            onChange={(e) => setCustomMaxPrice(e.target.value)}
          />
        </div>
      )}

      {/* Custom Location Input */}
      {selectedLocation === "Custom" && (
        <div className="mb-4">
          <input
            type="text"
            placeholder="Enter custom location"
            className="border p-2 rounded-md w-full"
            value={customLocation}
            onChange={(e) => setCustomLocation(e.target.value)}
          />
        </div>
      )}

      {/* Amenities Checklist */}
      <div className="mb-4">
        <p className="font-semibold mb-2">Amenities:</p>
        <div className="flex flex-wrap gap-4">
          {amenityOptions.map((amenity, index) => (
            <label key={index} className="flex items-center gap-1">
              <input
                type="checkbox"
                value={amenity}
                checked={selectedAmenities.includes(amenity)}
                onChange={handleAmenityChange}
              />
              {amenity}
            </label>
          ))}
        </div>
      </div>
    </div>
  );
};

export default FilterPanel;
