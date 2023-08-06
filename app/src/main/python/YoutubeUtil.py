import yt_dlp



def get_video_details(video_url):
    ydl = yt_dlp.YoutubeDL({})
    video_info = ydl.extract_info(video_url, download=False)
    return video_info
