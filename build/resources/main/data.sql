-- ============================================
-- INDIAN TRAIN TICKET BOOKING SYSTEM
-- Sample Data for Development/Testing
-- ============================================

-- Insert sample stations (Major Indian Railway stations)
INSERT INTO stations (station_code, station_name, city, state, zone, division, latitude, longitude, is_active, has_waiting_room, has_parking_facility, has_restaurant)
VALUES 
('NDLS', 'New Delhi', 'New Delhi', 'Delhi', 'Northern Railway', 'Delhi', 28.6419, 77.2194, true, true, true, true),
('BCT', 'Mumbai Central', 'Mumbai', 'Maharashtra', 'Western Railway', 'Mumbai', 18.9692, 72.8193, true, true, true, true),
('HWH', 'Howrah Junction', 'Kolkata', 'West Bengal', 'Eastern Railway', 'Howrah', 22.5839, 88.3426, true, true, true, true),
('MAS', 'Chennai Central', 'Chennai', 'Tamil Nadu', 'Southern Railway', 'Chennai', 13.0816, 80.2750, true, true, true, true),
('SBC', 'KSR Bengaluru', 'Bengaluru', 'Karnataka', 'South Western Railway', 'Bangalore', 12.9774, 77.5669, true, true, true, true),
('JP', 'Jaipur Junction', 'Jaipur', 'Rajasthan', 'North Western Railway', 'Jaipur', 26.9194, 75.7880, true, true, true, true),
('ADI', 'Ahmedabad Junction', 'Ahmedabad', 'Gujarat', 'Western Railway', 'Ahmedabad', 23.0256, 72.5996, true, true, true, true),
('LKO', 'Lucknow Junction', 'Lucknow', 'Uttar Pradesh', 'Northern Railway', 'Lucknow', 26.8383, 80.9184, true, true, true, true),
('PUNE', 'Pune Junction', 'Pune', 'Maharashtra', 'Central Railway', 'Pune', 18.5290, 73.8746, true, true, true, true),
('AGC', 'Agra Cantt', 'Agra', 'Uttar Pradesh', 'North Central Railway', 'Agra', 27.1591, 78.0109, true, true, true, true),
('CNB', 'Kanpur Central', 'Kanpur', 'Uttar Pradesh', 'North Central Railway', 'Allahabad', 26.4499, 80.3511, true, true, true, true),
('BPL', 'Bhopal Junction', 'Bhopal', 'Madhya Pradesh', 'West Central Railway', 'Bhopal', 23.2689, 77.4137, true, true, true, true),
('NGP', 'Nagpur Junction', 'Nagpur', 'Maharashtra', 'Central Railway', 'Nagpur', 21.1498, 79.0884, true, true, true, true),
('PNBE', 'Patna Junction', 'Patna', 'Bihar', 'East Central Railway', 'Danapur', 25.6044, 85.0877, true, true, true, true),
('GHY', 'Guwahati', 'Guwahati', 'Assam', 'Northeast Frontier Railway', 'Lumding', 26.1826, 91.7534, true, true, true, true),
('TVC', 'Thiruvananthapuram Central', 'Thiruvananthapuram', 'Kerala', 'Southern Railway', 'Thiruvananthapuram', 8.4897, 76.9503, true, true, true, true),
('HYB', 'Hyderabad Deccan', 'Hyderabad', 'Telangana', 'South Central Railway', 'Secunderabad', 17.3756, 78.4744, true, true, true, true),
('JAT', 'Jammu Tawi', 'Jammu', 'Jammu & Kashmir', 'Northern Railway', 'Ferozepur', 32.7207, 74.8674, true, true, true, true),
('CBE', 'Coimbatore Junction', 'Coimbatore', 'Tamil Nadu', 'Southern Railway', 'Salem', 10.9963, 76.9663, true, true, true, true),
('DDN', 'Dehradun', 'Dehradun', 'Uttarakhand', 'Northern Railway', 'Moradabad', 30.3248, 78.0420, true, true, true, true);

-- Insert sample trains
INSERT INTO trains (train_number, train_name, train_type, source_station_id, destination_station_id, 
                    total_seats1_a, total_seats2_a, total_seats3_a, total_seats_sl, total_seats_cc, total_seats2_s,
                    fare_rate1_a, fare_rate2_a, fare_rate3_a, fare_rate_sl, fare_rate_cc, fare_rate2_s,
                    is_active, has_pantry, coach_type)
VALUES 
('12301', 'Howrah Rajdhani Express', 'RAJDHANI', 1, 3, 24, 48, 180, 0, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB'),
('12302', 'New Delhi Rajdhani Express', 'RAJDHANI', 3, 1, 24, 48, 180, 0, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB'),
('12951', 'Mumbai Rajdhani Express', 'RAJDHANI', 1, 2, 24, 48, 192, 0, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB'),
('12952', 'New Delhi Rajdhani Express', 'RAJDHANI', 2, 1, 24, 48, 192, 0, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB'),
('12001', 'Bhopal Shatabdi Express', 'SHATABDI', 1, 12, 0, 0, 0, 0, 120, 0, 4.5, 2.8, 1.8, 0.9, 1.5, 0.6, true, true, 'LHB'),
('12002', 'New Delhi Shatabdi Express', 'SHATABDI', 12, 1, 0, 0, 0, 0, 120, 0, 4.5, 2.8, 1.8, 0.9, 1.5, 0.6, true, true, 'LHB'),
('12259', 'Sealdah Duronto Express', 'DURONTO', 1, 3, 24, 48, 180, 360, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB'),
('22691', 'KSR Bengaluru Rajdhani Express', 'RAJDHANI', 1, 5, 24, 48, 180, 0, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB'),
('12627', 'Karnataka Express', 'EXPRESS', 1, 5, 0, 48, 180, 480, 0, 72, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, false, 'LHB'),
('12621', 'Tamil Nadu Express', 'SUPERFAST', 1, 4, 24, 48, 180, 480, 0, 0, 4.5, 2.8, 1.8, 0.9, 1.2, 0.6, true, true, 'LHB');

-- Insert train running days (1=Sun, 2=Mon, ..., 7=Sat)
INSERT INTO train_running_days (train_id, day_of_week) VALUES 
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),  -- Howrah Rajdhani - Daily
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7),  -- Return - Daily
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7),  -- Mumbai Rajdhani - Daily
(4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7),  -- Return - Daily
(5, 2), (5, 3), (5, 4), (5, 5), (5, 6),                   -- Shatabdi - Mon-Fri
(6, 2), (6, 3), (6, 4), (6, 5), (6, 6),                   -- Return - Mon-Fri
(7, 1), (7, 4), (7, 7),                                   -- Duronto - Sun, Wed, Sat
(8, 2), (8, 5),                                           -- Bengaluru Rajdhani - Mon, Thu
(9, 1), (9, 2), (9, 3), (9, 4), (9, 5), (9, 6), (9, 7),  -- Karnataka Express - Daily
(10, 1), (10, 2), (10, 3), (10, 4), (10, 5), (10, 6), (10, 7); -- Tamil Nadu Express - Daily

-- Insert train schedules (route stops)
-- Howrah Rajdhani (12301): New Delhi -> Kanpur -> Allahabad -> Mughal Sarai -> Gaya -> Howrah
INSERT INTO train_schedules (train_id, station_id, stop_number, arrival_time, departure_time, distance_from_origin, halt_duration, day_offset, platform_number, is_active)
VALUES 
(1, 1, 1, '16:55:00', '17:00:00', 0, 5, 0, '1', true),      -- New Delhi (origin)
(1, 11, 2, '22:08:00', '22:10:00', 440, 2, 0, '2', true),   -- Kanpur
(1, 3, 3, '09:55:00', '10:00:00', 1451, 5, 1, '9', true);   -- Howrah (destination)

-- Mumbai Rajdhani (12951): New Delhi -> Kota -> Vadodara -> Surat -> Mumbai
INSERT INTO train_schedules (train_id, station_id, stop_number, arrival_time, departure_time, distance_from_origin, halt_duration, day_offset, platform_number, is_active)
VALUES 
(3, 1, 1, '16:25:00', '16:35:00', 0, 10, 0, '3', true),     -- New Delhi (origin)
(3, 7, 2, '23:45:00', '23:50:00', 493, 5, 0, '2', true),    -- Ahmedabad  
(3, 2, 3, '08:35:00', '08:35:00', 1384, 0, 1, '1', true);   -- Mumbai Central (destination)

-- Bhopal Shatabdi (12001): New Delhi -> Agra -> Jhansi -> Bhopal
INSERT INTO train_schedules (train_id, station_id, stop_number, arrival_time, departure_time, distance_from_origin, halt_duration, day_offset, platform_number, is_active)
VALUES 
(5, 1, 1, '06:00:00', '06:10:00', 0, 10, 0, '1', true),     -- New Delhi (origin)
(5, 10, 2, '08:08:00', '08:10:00', 188, 2, 0, '1', true),   -- Agra
(5, 12, 3, '14:20:00', '14:20:00', 703, 0, 0, '1', true);   -- Bhopal (destination)

-- Karnataka Express (12627): New Delhi -> Agra -> Jhansi -> Nagpur -> Bengaluru
INSERT INTO train_schedules (train_id, station_id, stop_number, arrival_time, departure_time, distance_from_origin, halt_duration, day_offset, platform_number, is_active)
VALUES 
(9, 1, 1, '22:15:00', '22:25:00', 0, 10, 0, '5', true),     -- New Delhi (origin)
(9, 10, 2, '01:10:00', '01:15:00', 195, 5, 1, '2', true),   -- Agra
(9, 13, 3, '11:40:00', '11:50:00', 808, 10, 1, '3', true),  -- Nagpur
(9, 5, 4, '06:40:00', '06:40:00', 2443, 0, 2, '1', true);   -- Bengaluru (destination)

-- Tamil Nadu Express (12621): New Delhi -> Agra -> Jhansi -> Nagpur -> Chennai
INSERT INTO train_schedules (train_id, station_id, stop_number, arrival_time, departure_time, distance_from_origin, halt_duration, day_offset, platform_number, is_active)
VALUES 
(10, 1, 1, '22:30:00', '22:40:00', 0, 10, 0, '6', true),    -- New Delhi (origin)
(10, 10, 2, '01:30:00', '01:35:00', 195, 5, 1, '3', true),  -- Agra
(10, 13, 3, '11:55:00', '12:05:00', 808, 10, 1, '4', true), -- Nagpur
(10, 4, 4, '07:00:00', '07:00:00', 2175, 0, 2, '4', true);  -- Chennai (destination)

-- Insert a sample user (password: password123)
INSERT INTO users (username, password, full_name, email, mobile_number, role, is_active, created_at)
VALUES ('testuser', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Test User', 'test@example.com', '9876543210', 'USER', true, CURRENT_TIMESTAMP);

-- Insert admin user (password: admin123)  
INSERT INTO users (username, password, full_name, email, mobile_number, role, is_active, created_at)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'System Admin', 'admin@irctc.com', '9999999999', 'ADMIN', true, CURRENT_TIMESTAMP);
