-- ============================================
-- Portfolio Database Seed Data
-- Initial data for development/testing
-- ============================================

-- Insert default admin user (password: Admin123!)
-- In production, change this password immediately
INSERT INTO users (id, email, password_hash, role) VALUES
    ('11111111-1111-1111-1111-111111111111', 'admin@portfolio.local', '$2a$10$placeholder_hash_change_me', 'admin');

-- Insert skill categories
INSERT INTO skill_categories (id, name, icon_url, display_order) VALUES
    ('c0000001-0000-0000-0000-000000000001', 'Langages', NULL, 1),
    ('c0000002-0000-0000-0000-000000000002', 'Frameworks', NULL, 2),
    ('c0000003-0000-0000-0000-000000000003', 'Outils', NULL, 3),
    ('c0000004-0000-0000-0000-000000000004', 'Soft Skills', NULL, 4),
    ('c0000005-0000-0000-0000-000000000005', 'Méthodologies', NULL, 5);

-- Example comment: Add your own skills, experiences, and realizations here
-- The admin user must create their profile through the API/backoffice
