import React, { useState, useCallback } from "react";

/**
 * Reusable form field component.
 * 
 * Props:
 * - id: Identifier for the field.
 * - label: Field label text.
 * - type: Input type (defaults to "text").
 * - value: Current field value.
 * - onChange: Callback to update value.
 * - placeholder: Placeholder text.
 * - error: Error message to display.
 * - isSelect: Render a select if true.
 * - options: Array of select options (each having 'value' and 'label').
 * - fullWidth: If true, spans the entire width.
 * - multiline: If true, renders a textarea.
 */
const FormField = React.memo(
  ({
    id,
    label,
    type = "text",
    value,
    onChange,
    placeholder,
    error,
    isSelect = false,
    options = [],
    fullWidth = false,
    multiline = false,
  }) => {
    // Apply full width if required, otherwise default to grid column span of 1.
    const containerClasses = fullWidth ? "col-span-full" : "col-span-1";

    return (
      <div className={containerClasses}>
        <label htmlFor={id} className="block text-gray-700 font-medium mb-1">
          {label}
        </label>
        {isSelect ? (
          // For the route type field, if a valid option is already selected,
          // filter out the default "Select route type" option.
          <select
            id={id}
            name={id}
            value={value}
            onChange={onChange}
            className="border border-gray-300 rounded-md p-2 w-full"
          >
            {options.map((opt) => {
              if (opt.value === "" && value !== "") return null;
              return (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              );
            })}
          </select>
        ) : multiline ? (
          <textarea
            id={id}
            name={id}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            className="border border-gray-300 rounded-md p-2 w-full h-24"
          />
        ) : (
          <input
            id={id}
            name={id}
            type={type}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            className="border border-gray-300 rounded-md p-2 w-full"
          />
        )}
        {error && <p className="text-red-500 text-sm mt-1">{error}</p>}
      </div>
    );
  }
);

/**
 * BusOwnerSignUp Component
 * Renders a sign-up form for bus owners with validations.
 */
const BusOwnerSignUp = () => {
  // Initial form state for all fields
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    phoneNumber: "",
    businessName: "",
    businessAddress: "",
    licenseNumber: "",
    busModel: "",
    busCapacity: "",
    registrationNumber: "",
    serviceAreas: "",
    routeType: "",
    password: "",
    confirmPassword: "",
  });

  // State to store error messages per field.
  const [errors, setErrors] = useState({});

  // Regex patterns for validations.
  // - Nepalese phone numbers start with 98 or 97 and have 10 digits total.
  const regexPatterns = {
    email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
    phone: /^(98|97)[0-9]{8}$/,
    license: /^[A-Za-z0-9-]+$/, // Valid characters for license.
    password: /^.{6,}$/,        // Minimum 6 characters.
  };

  /**
   * Helper: Checks if a field value is empty (after trimming).
   */
  const isEmpty = (value) => value.trim() === "";

  /**
   * handleChange: Updates the form field state.
   * Memoized using useCallback.
   */
  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  }, []);

  /**
   * validateForm: Checks all fields for validation errors.
   * All fields are required and numerical fields must not be negative.
   */
  const validateForm = () => {
    const newErrors = {};

    // Check required text fields.
    if (isEmpty(formData.fullName)) {
      newErrors.fullName = "Full name is required.";
    }

    if (isEmpty(formData.email)) {
      newErrors.email = "Email address is required.";
    } else if (!regexPatterns.email.test(formData.email)) {
      newErrors.email = "Please enter a valid email address.";
    }

    if (isEmpty(formData.phoneNumber)) {
      newErrors.phoneNumber = "Phone number is required.";
    } else if (!regexPatterns.phone.test(formData.phoneNumber)) {
      newErrors.phoneNumber = "Enter a valid Nepalese phone number (e.g., 98XXXXXXXX).";
    }

    if (isEmpty(formData.businessName)) {
      newErrors.businessName = "Business name is required.";
    }

    if (isEmpty(formData.businessAddress)) {
      newErrors.businessAddress = "Business address is required.";
    }

    if (isEmpty(formData.licenseNumber)) {
      newErrors.licenseNumber = "License number is required.";
    } else if (formData.licenseNumber.trim().charAt(0) === "-") {
      newErrors.licenseNumber = "License number cannot be negative.";
    } else if (!regexPatterns.license.test(formData.licenseNumber)) {
      newErrors.licenseNumber = "Invalid license format.";
    }

    if (isEmpty(formData.busModel)) {
      newErrors.busModel = "Bus model is required.";
    }

    if (isEmpty(formData.busCapacity)) {
      newErrors.busCapacity = "Bus capacity is required.";
    } else if (isNaN(formData.busCapacity)) {
      newErrors.busCapacity = "Bus capacity must be a number.";
    } else if (parseFloat(formData.busCapacity) < 0) {
      newErrors.busCapacity = "Bus capacity cannot be negative.";
    }

    if (isEmpty(formData.registrationNumber)) {
      newErrors.registrationNumber = "Registration number is required.";
    } else if (formData.registrationNumber.trim().charAt(0) === "-") {
      newErrors.registrationNumber = "Registration number cannot be negative.";
    }

    if (isEmpty(formData.serviceAreas)) {
      newErrors.serviceAreas = "Service areas is required.";
    }

    if (isEmpty(formData.routeType)) {
      newErrors.routeType = "Route type is required.";
    }

    if (isEmpty(formData.password)) {
      newErrors.password = "Password is required.";
    } else if (!regexPatterns.password.test(formData.password)) {
      newErrors.password = "Password must be at least 6 characters.";
    }

    if (isEmpty(formData.confirmPassword)) {
      newErrors.confirmPassword = "Please confirm your password.";
    } else if (formData.confirmPassword !== formData.password) {
      newErrors.confirmPassword = "Passwords do not match.";
    }

    return newErrors;
  };

  /**
   * handleSubmit: Validates form and processes submission.
   */
  const handleSubmit = useCallback(
    (e) => {
      e.preventDefault();
      const validationErrors = validateForm();
      if (Object.keys(validationErrors).length > 0) {
        setErrors(validationErrors);
        return;
      }
      setErrors({});
      console.log("Form submitted:", formData);
      alert("Form submitted successfully!");
    },
    [formData]
  );

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4">
      <div className="w-full max-w-4xl bg-white rounded-md shadow-md p-6">
        {/* Page Title */}
        <h1 className="text-2xl font-bold mb-6">Bus Owner Sign Up</h1>

        <form onSubmit={handleSubmit} noValidate>
          {/* Personal / Business Information */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            {/* Full Name (full width) */}
            <FormField
              id="fullName"
              label="Full Name"
              placeholder="Enter your full name"
              value={formData.fullName}
              onChange={handleChange}
              error={errors.fullName}
              fullWidth
            />

            <FormField
              id="email"
              label="Email Address"
              type="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              error={errors.email}
            />

            <FormField
              id="phoneNumber"
              label="Phone Number"
              placeholder="Enter your phone number"
              value={formData.phoneNumber}
              onChange={handleChange}
              error={errors.phoneNumber}
            />

            {/* Business Name (full width) */}
            <FormField
              id="businessName"
              label="Business Name"
              placeholder="Enter business name"
              value={formData.businessName}
              onChange={handleChange}
              error={errors.businessName}
              fullWidth
            />

            {/* Business Address (textarea, full width) */}
            <FormField
              id="businessAddress"
              label="Business Address"
              placeholder="Enter business address"
              value={formData.businessAddress}
              onChange={handleChange}
              error={errors.businessAddress}
              multiline
              fullWidth
            />

            {/* License Number (full width) */}
            <FormField
              id="licenseNumber"
              label="License Number"
              placeholder="Enter license number"
              value={formData.licenseNumber}
              onChange={handleChange}
              error={errors.licenseNumber}
              fullWidth
            />
          </div>

          {/* Bus Details */}
          <h2 className="text-xl font-semibold mb-4">Bus Details</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            {/* Bus Model (full width) */}
            <FormField
              id="busModel"
              label="Bus Model"
              placeholder="Enter bus model"
              value={formData.busModel}
              onChange={handleChange}
              error={errors.busModel}
              fullWidth
            />

            <FormField
              id="busCapacity"
              label="Bus Capacity"
              type="number"
              placeholder="Enter seating capacity"
              value={formData.busCapacity}
              onChange={handleChange}
              error={errors.busCapacity}
            />

            <FormField
              id="registrationNumber"
              label="Registration Number"
              placeholder="Enter registration number"
              value={formData.registrationNumber}
              onChange={handleChange}
              error={errors.registrationNumber}
            />
          </div>

          {/* Operational Preferences */}
          <h2 className="text-xl font-semibold mb-4">Operational Preferences</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <FormField
              id="serviceAreas"
              label="Service Areas"
              placeholder="Enter service areas"
              value={formData.serviceAreas}
              onChange={handleChange}
              error={errors.serviceAreas}
            />
            <FormField
              id="routeType"
              label="Route Type"
              isSelect
              options={[
                { value: "", label: "Select route type" },
                { value: "Urban", label: "Urban" },
                { value: "Intercity", label: "Intercity" },
                { value: "Rural", label: "Rural" },
              ]}
              value={formData.routeType}
              onChange={handleChange}
              error={errors.routeType}
            />
          </div>

          {/* Password Fields */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
            <FormField
              id="password"
              label="Password"
              type="password"
              placeholder="Enter password"
              value={formData.password}
              onChange={handleChange}
              error={errors.password}
            />
            <FormField
              id="confirmPassword"
              label="Confirm Password"
              type="password"
              placeholder="Confirm password"
              value={formData.confirmPassword}
              onChange={handleChange}
              error={errors.confirmPassword}
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition duration-200"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default BusOwnerSignUp;
