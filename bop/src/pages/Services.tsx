import React, { useState, useEffect } from 'react';
import { api } from '../lib/api';

export function Services() {
  const [categories, setCategories] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchServices = async () => {
      try {
        const response = await api.get('/catalog/categories');
        setCategories(response.data.data.content);
      } catch (error) {
        console.error('Failed to fetch catalog', error);
      } finally {
        setLoading(false);
      }
    };
    fetchServices();
  }, []);

  if (loading) return <div>Loading service catalog...</div>;

  return (
    <div className="max-w-6xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Service Catalog</h1>
          <p className="text-sm text-gray-500">Manage categories, services, and pricing.</p>
        </div>
        <button className="px-4 py-2 bg-primary text-white rounded-md shadow-sm text-sm font-medium hover:bg-primary-hover">
          Add Category
        </button>
      </div>

      <div className="space-y-6">
        {categories.map((category) => (
          <div key={category.id} className="bg-white shadow rounded-lg overflow-hidden">
            <div className="px-6 py-4 border-b border-gray-200 bg-gray-50 flex justify-between items-center">
              <h3 className="text-lg font-medium text-gray-900">{category.name}</h3>
              <button className="text-sm text-primary hover:text-primary-hover">Add Service</button>
            </div>
            <ul className="divide-y divide-gray-200">
              {category.services?.map((service: any) => (
                <li key={service.id} className="px-6 py-4 flex justify-between items-center">
                  <div>
                    <div className="text-sm font-medium text-gray-900">{service.name}</div>
                    <div className="text-sm text-gray-500">{service.durationMinutes} min • {service.bufferMinutes} min buffer</div>
                  </div>
                  <div className="text-sm font-bold text-gray-900">
                    ${service.price}
                  </div>
                </li>
              ))}
              {(!category.services || category.services.length === 0) && (
                <li className="px-6 py-4 text-sm text-gray-500 text-center">
                  No services in this category.
                </li>
              )}
            </ul>
          </div>
        ))}
        {categories.length === 0 && (
          <div className="bg-white shadow rounded-lg p-6 text-center text-gray-500">
            No categories found. Create one to get started.
          </div>
        )}
      </div>
    </div>
  );
}
