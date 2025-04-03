import Layout from "../Layout/Layout";

export default function AboutUs() {
    return (
        <Layout>
            <div className="bg-gradient-to-b from-blue-50 to-white min-h-screen w-full flex justify-center p-10">
                <div className="max-w-5xl w-full bg-white shadow-2xl rounded-lg p-12">
                    {/* Heading */}
                    <h1 className="text-5xl font-extrabold text-gray-900 text-left border-l-8 border-blue-500 pl-4">
                        About Us
                    </h1>

                    {/* Content Section */}
                    <div className="text-left mt-8 space-y-8">
                        <div>
                            <h3 className="text-3xl font-bold text-gray-800">
                                Welcome to <span className="text-blue-600">EasyTrip</span> – Your Gateway to Comfortable Stays in Nepal!
                            </h3>
                            <p className="text-gray-700 mt-3 leading-relaxed">
                                At <span className="font-semibold">EasyTrip</span>, we believe every traveler deserves a hassle-free and memorable stay.
                                Our platform connects travelers with the best guesthouses across Nepal, ensuring comfort, convenience, and affordability.
                            </p>
                        </div>

                        <div>
                            <h3 className="text-3xl font-bold text-gray-900">Our Vision</h3>
                            <p className="text-gray-700 mt-3 leading-relaxed">
                                We aim to revolutionize the way travelers experience Nepal by making accommodation booking effortless, reliable, and enjoyable.
                            </p>
                        </div>

                        <div>
                            <h3 className="text-3xl font-bold text-gray-900">Join Us on Your Next Adventure</h3>
                            <p className="text-gray-700 mt-3 leading-relaxed">
                                At EasyTrip, we’re more than just a booking service—we're your travel companion. Start your adventure with EasyTrip today!
                            </p>
                            <p className="text-gray-900 font-extrabold text-lg mt-2">Book. Travel. Experience.</p>
                        </div>

                        <div>
                            <h3 className="text-3xl font-bold text-gray-900">Who We Are</h3>
                            <p className="text-gray-700 mt-3 leading-relaxed">
                                EasyTrip is a Nepal-based online booking platform designed to simplify your travel experience.
                                We partner with trusted accommodations all over Nepal, ensuring that every traveler finds their perfect stay.
                            </p>
                        </div>

                        {/* Why Choose Us Section */}
                        <div>
                            <h3 className="text-3xl font-bold text-gray-900">Why Choose EasyTrip?</h3>
                            <ul className="text-gray-700 mt-4 text-lg space-y-3">
                                <li className="flex items-start"><span className="text-blue-500 font-bold mr-2">✔</span> <span><span className="font-semibold">Wide Selection:</span> A diverse range of guesthouses from budget-friendly to premium stays.</span></li>
                                <li className="flex items-start"><span className="text-blue-500 font-bold mr-2">✔</span> <span><span className="font-semibold">User-Friendly Platform:</span> Easy booking process with real-time availability.</span></li>
                                <li className="flex items-start"><span className="text-blue-500 font-bold mr-2">✔</span> <span><span className="font-semibold">Verified Listings:</span> Quality service, cleanliness, and comfort assured.</span></li>
                                <li className="flex items-start"><span className="text-blue-500 font-bold mr-2">✔</span> <span><span className="font-semibold">24/7 Customer Support:</span> Our team is always ready to assist you.</span></li>
                                <li className="flex items-start"><span className="text-blue-500 font-bold mr-2">✔</span> <span><span className="font-semibold">Local Expertise:</span> We provide insider tips and recommendations.</span></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </Layout>
    );
}