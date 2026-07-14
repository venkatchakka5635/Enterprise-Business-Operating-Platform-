import React from 'react';
import { useAuth } from '../context/AuthContext';
import { LogOut, User as UserIcon } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

export function Header() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="flex items-center justify-between h-16 px-6 bg-white border-b border-gray-200 shrink-0 shadow-sm">
      <div className="flex items-center">
        <h1 className="text-lg font-semibold text-gray-900 truncate">
          {/* We can potentially inject page title here via context or router */}
        </h1>
      </div>
      <div className="flex items-center space-x-4">
        <div className="flex items-center space-x-2 text-sm">
          <div className="h-8 w-8 rounded-full bg-primary-light text-primary flex items-center justify-center font-bold">
            {user?.firstName?.[0]}{user?.lastName?.[0]}
          </div>
          <div className="hidden md:block">
            <p className="text-sm font-medium text-gray-700">
              {user?.firstName} {user?.lastName}
            </p>
            <p className="text-xs text-gray-500 capitalize">{user?.role.toLowerCase()}</p>
          </div>
        </div>
        <button
          onClick={handleLogout}
          className="p-2 text-gray-400 hover:text-gray-600 transition-colors rounded-full hover:bg-gray-100"
          title="Logout"
        >
          <LogOut className="h-5 w-5" />
        </button>
      </div>
    </header>
  );
}
