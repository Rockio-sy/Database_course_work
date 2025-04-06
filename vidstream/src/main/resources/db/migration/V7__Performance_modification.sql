-- ===========================
-- 1. Performance: Add Indexes
-- ===========================

CREATE INDEX idx_rate_video_id ON rate(video_id);
CREATE INDEX idx_rate_user_id ON rate(user_id);
CREATE INDEX idx_watchlist_user_id ON watch_list(user_id);
CREATE INDEX idx_watchlist_video_id ON watch_list(video_id);
CREATE INDEX idx_subscription_user_id ON subscription(user_id);
CREATE INDEX idx_video_created_at ON video(created_at DESC);
CREATE INDEX idx_watch_list_created_at ON watch_list(created_at DESC);

-- ===========================
-- 2. Triggers for Soft Delete
-- ===========================

CREATE FUNCTION soft_delete() RETURNS TRIGGER AS $$
BEGIN
    NEW.removed_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_soft_delete_video
BEFORE DELETE ON video
FOR EACH ROW EXECUTE FUNCTION soft_delete();

CREATE TRIGGER trigger_soft_delete_user
BEFORE DELETE ON users
FOR EACH ROW EXECUTE FUNCTION soft_delete();

-- ====================================
-- 3. Function & Trigger for Auto Ratings
-- ====================================

CREATE FUNCTION calculate_average_rating(vid BIGINT) RETURNS NUMERIC AS $$
DECLARE avg_rating NUMERIC;
BEGIN
    SELECT COALESCE(AVG(rate_value), 0) INTO avg_rating FROM rate WHERE video_id = vid;
    RETURN avg_rating;
END;
$$ LANGUAGE plpgsql;

CREATE FUNCTION update_video_avg_rating() RETURNS TRIGGER AS $$
BEGIN
    UPDATE video
    SET avg_rating = calculate_average_rating(NEW.video_id)
    WHERE id = NEW.video_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_avg_rating
AFTER INSERT OR UPDATE OR DELETE ON rate
FOR EACH ROW EXECUTE FUNCTION update_video_avg_rating();

-- =================================
-- 4. Stored Procedure to Add Users
-- =================================

CREATE PROCEDURE add_user(IN full_name VARCHAR(20), IN username VARCHAR(20),
                          IN email VARCHAR(50), IN password VARCHAR(100), IN role VARCHAR(10))
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO users (full_name, username, email, password, role, created_at)
    VALUES (full_name, username, email, password, role, CURRENT_TIMESTAMP);
END;
$$;

-- =================================
-- 5. View for Videos with Ratings
-- =================================

CREATE VIEW video_with_ratings AS
SELECT v.id, v.title, v.uploader_id, v.created_at, v.path,
       COALESCE(AVG(r.rate_value), 0) AS avg_rating
FROM video v
LEFT JOIN rate r ON v.id = r.video_id
GROUP BY v.id;
