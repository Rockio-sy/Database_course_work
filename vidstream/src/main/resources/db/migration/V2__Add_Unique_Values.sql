ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE(email);
ALTER TABLE users ADD CONSTRAINT unique_username UNIQUE(username);
ALTER TABLE video ADD CONSTRAINT unique_video  UNIQUE(title, uploader_id);
ALTER TABLE watch_list ADD CONSTRAINT unique_video_in_Watch_list UNIQUE(video_id, user_id);
