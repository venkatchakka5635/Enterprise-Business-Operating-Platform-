import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { cn } from '../lib/utils';
import { 
  LayoutDashboard, Users, Calendar, Scissors, 
  FileText, Workflow, CreditCard, Bell, Activity, Settings 
} from 'lucide-react';
import { useAuth } from '../context/AuthContext';

export function Sidebar() {
  const location = useLocation();
  const { user } = useAuth();

  const navigation = [
    { name: 'Dashboard', href: '/', icon: LayoutDashboard },
    { name: 'Appointments', href: '/appointments', icon: Calendar },
    { name: 'Customers', href: '/customers', icon: Users },
    { name: 'Services', href: '/services', icon: Scissors },
    { name: 'Billing', href: '/billing', icon: CreditCard },
    { name: 'Forms', href: '/forms', icon: FileText },
    { name: 'Workflows', href: '/workflows', icon: Workflow },
    { name: 'Staff', href: '/staff', icon: Activity },
    { name: 'Notifications', href: '/notifications', icon: Bell },
    { name: 'Settings', href: '/settings', icon: Settings, roles: ['OWNER', 'MANAGER'] },
  ];

  const filteredNav = navigation.filter(item => {
    if (item.roles && user) {
      return item.roles.includes(user.role);
    }
    return true;
  });

  return (
    <div className="flex flex-col w-64 bg-white border-r border-gray-200">
      <div className="flex items-center justify-center h-16 border-b border-gray-200 px-4">
        <span className="text-xl font-bold text-primary truncate w-full text-center">
          {user?.tenantName || 'EBOP'}
        </span>
      </div>
      <div className="flex-1 overflow-y-auto py-4">
        <nav className="px-3 space-y-1">
          {filteredNav.map((item) => {
            const isActive = location.pathname === item.href || 
                             (item.href !== '/' && location.pathname.startsWith(item.href));
            
            return (
              <Link
                key={item.name}
                to={item.href}
                className={cn(
                  isActive
                    ? 'bg-primary-light text-primary font-medium'
                    : 'text-text-secondary hover:bg-gray-50 hover:text-text-primary',
                  'group flex items-center px-3 py-2 text-sm font-medium rounded-md transition-colors'
                )}
              >
                <item.icon
                  className={cn(
                    isActive ? 'text-primary' : 'text-gray-400 group-hover:text-gray-500',
                    'flex-shrink-0 -ml-1 mr-3 h-5 w-5'
                  )}
                  aria-hidden="true"
                />
                <span className="truncate">{item.name}</span>
              </Link>
            );
          })}
        </nav>
      </div>
    </div>
  );
}
