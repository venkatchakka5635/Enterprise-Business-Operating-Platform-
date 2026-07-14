import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { DashboardLayout } from './components/DashboardLayout';
import { PublicLayout } from './components/PublicLayout';
import { Login } from './pages/Login';
import { Settings } from './pages/Settings';
import { Staff } from './pages/Staff';
import { Services } from './pages/Services';
import { Dashboard } from './pages/Dashboard';

// Placeholder pages for modules
const Appointments = () => <div className="p-6 bg-white rounded shadow-sm"><h1>Appointments coming soon</h1></div>;
const Customers = () => <div className="p-6 bg-white rounded shadow-sm"><h1>Customers coming soon</h1></div>;
const Billing = () => <div className="p-6 bg-white rounded shadow-sm"><h1>Billing coming soon</h1></div>;
const Forms = () => <div className="p-6 bg-white rounded shadow-sm"><h1>Forms coming soon</h1></div>;
const Workflows = () => <div className="p-6 bg-white rounded shadow-sm"><h1>Workflows coming soon</h1></div>;
const Notifications = () => <div className="p-6 bg-white rounded shadow-sm"><h1>Notifications coming soon</h1></div>;

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<PublicLayout />}>
            <Route path="/login" element={<Login />} />
          </Route>
          
          <Route element={<DashboardLayout />}>
            <Route path="/" element={<Dashboard />} />
            <Route path="/appointments/*" element={<Appointments />} />
            <Route path="/customers/*" element={<Customers />} />
            <Route path="/services/*" element={<Services />} />
            <Route path="/billing/*" element={<Billing />} />
            <Route path="/forms/*" element={<Forms />} />
            <Route path="/workflows/*" element={<Workflows />} />
            <Route path="/staff/*" element={<Staff />} />
            <Route path="/notifications/*" element={<Notifications />} />
            <Route path="/settings/*" element={<Settings />} />
          </Route>

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
