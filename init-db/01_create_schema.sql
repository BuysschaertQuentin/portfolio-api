-- ============================================
-- Portfolio Database Schema
-- PostgreSQL 17
-- Convention: singular table names (JPA best practice)
-- ============================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- CORE ENTITIES
-- ============================================

-- User table (for backoffice authentication)
CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'admin' CHECK (role IN ('admin', 'superadmin')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Profile table (personal and professional info)
CREATE TABLE profile (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL UNIQUE REFERENCES "user"(id) ON DELETE CASCADE,
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
CREATE TABLE contact (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    profile_id UUID NOT NULL UNIQUE REFERENCES profile(id) ON DELETE CASCADE,
    email VARCHAR(255),
    phone VARCHAR(50),
    address_city VARCHAR(100),
    address_country VARCHAR(100),
    address_zip_code VARCHAR(20)
);

-- Social link table (dynamic social links)
CREATE TABLE social_link (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    profile_id UUID NOT NULL REFERENCES profile(id) ON DELETE CASCADE,
    platform VARCHAR(50) NOT NULL CHECK (platform IN ('linkedin', 'github', 'twitter', 'website', 'youtube', 'other')),
    url VARCHAR(500) NOT NULL,
    display_order INTEGER NOT NULL DEFAULT 0,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- SKILLS ENTITIES
-- ============================================

-- Skill category table
CREATE TABLE skill_category (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL UNIQUE,
    icon_url VARCHAR(500),
    display_order INTEGER NOT NULL DEFAULT 0
);

-- Skill table
CREATE TABLE skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    category_id UUID NOT NULL REFERENCES skill_category(id) ON DELETE CASCADE,
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

-- Experience table (jobs, training, certifications)
CREATE TABLE experience (
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
CREATE TABLE experience_skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    experience_id UUID NOT NULL REFERENCES experience(id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skill(id) ON DELETE CASCADE,
    UNIQUE(experience_id, skill_id)
);

-- ============================================
-- REALIZATION ENTITIES
-- ============================================

-- Realization table (projects)
CREATE TABLE realization (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    experience_id UUID REFERENCES experience(id) ON DELETE SET NULL,
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
CREATE TABLE realization_skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    realization_id UUID NOT NULL REFERENCES realization(id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skill(id) ON DELETE CASCADE,
    UNIQUE(realization_id, skill_id)
);

-- Media table (for realizations)
CREATE TABLE media (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    realization_id UUID NOT NULL REFERENCES realization(id) ON DELETE CASCADE,
    type VARCHAR(30) NOT NULL CHECK (type IN ('image', 'video', 'document', 'screenshot')),
    url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(500),
    caption TEXT,
    display_order INTEGER NOT NULL DEFAULT 0
);

-- ============================================
-- INDEXES
-- ============================================

CREATE INDEX idx_profile_user_id ON profile(user_id);
CREATE INDEX idx_contact_profile_id ON contact(profile_id);
CREATE INDEX idx_social_link_profile_id ON social_link(profile_id);
CREATE INDEX idx_skill_category_id ON skill(category_id);
CREATE INDEX idx_skill_type ON skill(type);
CREATE INDEX idx_skill_highlighted ON skill(is_highlighted);
CREATE INDEX idx_experience_type ON experience(type);
CREATE INDEX idx_experience_visible ON experience(is_visible);
CREATE INDEX idx_experience_skill_experience_id ON experience_skill(experience_id);
CREATE INDEX idx_experience_skill_skill_id ON experience_skill(skill_id);
CREATE INDEX idx_realization_status ON realization(status);
CREATE INDEX idx_realization_featured ON realization(is_featured);
CREATE INDEX idx_realization_slug ON realization(slug);
CREATE INDEX idx_realization_skill_realization_id ON realization_skill(realization_id);
CREATE INDEX idx_realization_skill_skill_id ON realization_skill(skill_id);
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

CREATE TRIGGER update_user_updated_at 
    BEFORE UPDATE ON "user" 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_profile_updated_at 
    BEFORE UPDATE ON profile 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_social_link_updated_at 
    BEFORE UPDATE ON social_link 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_skill_updated_at 
    BEFORE UPDATE ON skill 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_experience_updated_at 
    BEFORE UPDATE ON experience 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_realization_updated_at 
    BEFORE UPDATE ON realization 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
