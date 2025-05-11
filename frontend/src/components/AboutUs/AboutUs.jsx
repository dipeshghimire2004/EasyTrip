import Layout from "../Layout/Layout";
import { CheckCircleIcon } from "@heroicons/react/24/solid"; // Requires Heroicons or you can use Lucide

export default function AboutUs() {
    return (
        <Layout>
            <div className="bg-gradient-to-b from-blue-100 to-white min-h-screen w-full flex justify-center p-6 md:p-10">
                <div className="max-w-6xl w-full bg-white shadow-xl rounded-3xl px-6 py-10 md:p-16 space-y-14">

                    {/* Title */}
                    <div>
                        <h1 className="text-4xl sm:text-5xl font-extrabold text-gray-800 border-l-8 text-green-500 pl-4">
                            About Us
                        </h1>
                        <p className="text-lg text-gray-600 mt-4 pl-4 border-l-4 border-blue-200">
                            Discover who we are, what drives us, and why EasyTrip is the best travel companion for your next trip in Nepal.
                        </p>
                    </div>

                    {/* Welcome Section */}
                    <section className="space-y-4">
                        <h2 className="text-3xl font-bold text-green-500">Welcome to EasyTrip</h2>
                        <p className="text-gray-700 leading-relaxed text-lg">
                            At <strong>EasyTrip</strong>, we believe every traveler deserves a hassle-free and memorable stay.
                            Our platform connects you with the best guesthouses across Nepal — combining comfort, convenience, and affordability.
                        </p>
                    </section>

                    {/* Vision */}
                    <section className="bg-blue-50 p-6 md:p-10 rounded-2xl shadow-inner space-y-4">
                        <h2 className="text-3xl font-bold text-gray-800">Our Vision</h2>
                        <p className="text-gray-700 text-lg">
                            To revolutionize the way travelers experience Nepal by making accommodation booking seamless, reliable, and joyful.
                        </p>
                    </section>

                    {/* Who We Are */}
                    <section className="space-y-4">
                        <h2 className="text-3xl font-bold text-gray-800">Who We Are</h2>
                        <p className="text-gray-700 text-lg">
                            EasyTrip is a Nepal-based platform dedicated to simplifying your travel. We work with trusted accommodations across the country to ensure you always have a great stay.
                        </p>
                    </section>

                    {/* Why Choose Us */}
                    <section className="bg-blue-50 p-6 md:p-10 rounded-2xl shadow-inner space-y-6">
                        <h2 className="text-3xl font-bold text-gray-800">Why Choose EasyTrip?</h2>
                        <ul className="space-y-4 text-gray-700 text-lg">
                            {[
                                ["Wide Selection", "Diverse guesthouses from budget to luxury."],
                                ["User-Friendly Platform", "Effortless booking with real-time availability."],
                                ["Verified Listings", "Clean, comfortable, and quality stays."],
                                ["24/7 Customer Support", "Always here when you need us."],
                                ["Local Expertise", "Insider travel tips & curated recommendations."]
                            ].map(([title, desc], i) => (
                                <li key={i} className="flex items-start gap-3">
                                    <CheckCircleIcon className="h-6 w-6 text-green-400 mt-1" />
                                    <span><strong>{title}:</strong> {desc}</span>
                                </li>
                            ))}
                        </ul>
                    </section>

                    {/* Join Us CTA */}
                    <section className="text-center space-y-4">
                        <h2 className="text-3xl font-bold text-gray-900">Join Us on Your Next Adventure</h2>
                        <p className="text-gray-700 text-lg max-w-3xl mx-auto">
                            More than just a booking service, EasyTrip is your travel companion. Let us help you create unforgettable experiences.
                        </p>
                        <p className="text-green-700 font-extrabold text-xl">Book. Travel. Experience.</p>
                    </section>
                </div>
            </div>
        </Layout>
    );
}
