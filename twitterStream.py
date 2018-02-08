#import default python libraries
import json
#import tweepy library
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
#import panda library
import pandas as pd
#Variables
access_token = "957326291543515136-bMc0bb2H6VrSBEOwC9bYn8asAWEUBge"
access_token_secret = "JgKtiW8qe107ImrTMSMtRAbseA0HQMrSpvmrTTGlCx9lX"
consumer_key = "FaVG24EilMLhWvE4aVFZT2ag9"
consumer_secret = "cNRHs7GsP6UbgNU8Aa3x6d58ZFqvtfBjDE6DwzJRl8opiz2pdT"

class StdOutListener(StreamListener):
    def on_data(self,data):
        print(data)
        return True
    def on_error(self,status):
        print(status)
if __name__ == '__main__':

    #This handles Twitter authetification and the connection to Twitter Streaming API
    l = StdOutListener()
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)
    stream = Stream(auth, l)

    #This line filter Twitter Streams to capture data by the keywords: 'hurricane', 'flood', 'evacuation'
    stream.filter(track=['hurricane', 'flood', 'evacuation'])
    tweets_data_path = '../data/twitter_data.txt'
    tweets_data = []
    tweets_file = open(tweets_data_path, "r")
    for line in tweets_file:
        try:
            tweet = json.loads(line)
            tweets_data.append(tweet)
        except:
            continue
    tweets_data_path = '../data/twitter_data.txt'
