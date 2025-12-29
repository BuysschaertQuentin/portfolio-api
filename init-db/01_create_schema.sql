-- ============================================
-- Portfolio Database Schema
-- PostgreSQL 17
-- ============================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- CORE ENTITIES
-- ============================================

-- Users table (for backoffice authentication)
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'admin' CHECK (role IN ('admin', 'superadmin')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Profile table (personal and professional info)
CREATE TABLE profiles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    title VARCHAR(200),
    photo_url VARCHAR(500),
    bio TEXT,
    professional_project TEXT,
    personal_project TEXT,
    qualities TEXT,
    interests TEXT,
    cv_url VARCHAR(500),
    disponibility VARCHAR(50) CHECK (disponibility IN ('available', 'not_available', 'available_from')),
    disponibility_date DATE,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Contact table (contact details)
CREATE TABLE contacts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    profile_id UUID NOT NULL UNIQUE REFERENCES profiles(id) ON DELETE CASCADE,
    email VARCHAR(255),
    phone VARCHAR(50),
    address_city VARCHAR(100),
    address_country VARCHAR(100),
    address_zip_code VARCHAR(20)
);

-- Social links table (dynamic social links)
CREATE TABLE social_links (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    profile_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
    platform VARCHAR(50) NOT NULL CHECK (platform IN ('linkedin', 'github', 'twitter', 'website', 'youtube', 'other')),
    url VARCHAR(500) NOT NULL,
    display_order INTEGER NOT NULL DEFAULT 0,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- SKILLS ENTITIES
-- ============================================

-- Skill categories table
CREATE TABLE skill_categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL UNIQUE,
    icon_url VARCHAR(500),
    display_order INTEGER NOT NULL DEFAULT 0
);

-- Skills table
CREATE TABLE skills (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    category_id UUID NOT NULL REFERENCES skill_categories(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('soft_skill', 'hard_skill')),
    icon_url VARCHAR(500),
    proficiency_level INTEGER CHECK (proficiency_level BETWEEN 1 AND 5),
    definition TEXT,
    professional_context TEXT,
    self_criticism TEXT,
    is_highlighted BOOLEAN DEFAULT FALSE,
    display_order INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- EXPERIENCE ENTITIES
-- ============================================

-- Experiences table (jobs, training, certifications)
CREATE TABLE experiences (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    type VARCHAR(30) NOT NULL CHECK (type IN ('job', 'internship', 'apprenticeship', 'training', 'certification')),
    start_date DATE NOT NULL,
    end_date DATE,
    is_current BOOLEAN DEFAULT FALSE,
    title VARCHAR(200) NOT NULL,
    organization_name VARCHAR(200) NOT NULL,
    organization_logo_url VARCHAR(500),
    organization_url VARCHAR(500),
    location VARCHAR(200),
    description TEXT,
    missions TEXT,
    achievements TEXT,
    is_visible BOOLEAN DEFAULT TRUE,
    display_order INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Experience-Skill junction table
CREATE TABLE experience_skills (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    experience_id UUID NOT NULL REFERENCES experiences(id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skills(id) ON DELETE CASCADE,
    UNIQUE(experience_id, skill_id)
);

-- ============================================
-- REALIZATION ENTITIES
-- ============================================

-- Realizations table (projects)
CREATE TABLE realizations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    experience_id UUID REFERENCES experiences(id) ON DELETE SET NULL,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    short_description TEXT,
    full_presentation TEXT,
    objectives TEXT,
    context TEXT,
    stakes TEXT,
    steps TEXT,
    actors_interactions TEXT,
    results TEXT,
    next_steps TEXT,
    critical_view TEXT,
    thumbnail_url VARCHAR(500),
    demo_url VARCHAR(500),
    repo_url VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'draft' CHECK (status IN ('draft', 'published', 'archived', 'private')),
    project_date DATE,
    is_featured BOOLEAN DEFAULT FALSE,
    display_order INTEGER NOT NULL DEFAULT 0,
    meta_title VARCHAR(200),
    meta_description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Realization-Skill junction table
CREATE TABLE realization_skills (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    realization_id UUID NOT NULL REFERENCES realizations(id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skills(id) ON DELETE CASCADE,
    UNIQUE(realization_id, skill_id)
);

-- Media table (for realizations)
CREATE TABLE media (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    realization_id UUID NOT NULL REFERENCES realizations(id) ON DELETE CASCADE,
    type VARCHAR(30) NOT NULL CHECK (type IN ('image', 'video', 'document', 'screenshot')),
    url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(500),
    caption TEXT,
    display_order INTEGER NOT NULL DEFAULT 0
);

-- ============================================
-- INDEXES
-- ============================================

CREATE INDEX idx_profiles_user_id ON profiles(user_id);
CREATE INDEX idx_contacts_profile_id ON contacts(profile_id);
CREATE INDEX idx_social_links_profile_id ON social_links(profile_id);
CREATE INDEX idx_skills_category_id ON skills(category_id);
CREATE INDEX idx_skills_type ON skills(type);
CREATE INDEX idx_skills_highlighted ON skills(is_highlighted);
CREATE INDEX idx_experiences_type ON experiences(type);
CREATE INDEX idx_experiences_visible ON experiences(is_visible);
CREATE INDEX idx_experience_skills_experience_id ON experience_skills(experience_id);
CREATE INDEX idx_experience_skills_skill_id ON experience_skills(skill_id);
CREATE INDEX idx_realizations_status ON realizations(status);
CREATE INDEX idx_realizations_featured ON realizations(is_featured);
CREATE INDEX idx_realizations_slug ON realizations(slug);
CREATE INDEX idx_realization_skills_realization_id ON realization_skills(realization_id);
CREATE INDEX idx_realization_skills_skill_id ON realization_skills(skill_id);
CREATE INDEX idx_media_realization_id ON media(realization_id);

-- ============================================
-- TRIGGERS FOR updated_at
-- ============================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_users_updated_at 
    BEFORE UPDATE ON users 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_profiles_updated_at 
    BEFORE UPDATE ON profiles 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_social_links_updated_at 
    BEFORE UPDATE ON social_links 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_skills_updated_at 
    BEFORE UPDATE ON skills 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_experiences_updated_at 
    BEFORE UPDATE ON experiences 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_realizations_updated_at 
    BEFORE UPDATE ON realizations 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
