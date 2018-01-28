loadCSV <-function(searchKey,numTweets)
{
library(twitteR) #installs TwitteR library (twitteR) #loads TwitteR
api_key <- "FaVG24EilMLhWvE4aVFZT2ag9" #in the quotes, put your API key 
api_secret <- "cNRHs7GsP6UbgNU8Aa3x6d58ZFqvtfBjDE6DwzJRl8opiz2pdT" #in the quotes, put your API secret token 
token <- "957326291543515136-bMc0bb2H6VrSBEOwC9bYn8asAWEUBge" #in the quotes, put your token
token_secret <- "JgKtiW8qe107ImrTMSMtRAbseA0HQMrSpvmrTTGlCx9lX" #in the quotes, put your token secret 
setup_twitter_oauth(api_key, api_secret, token, token_secret)
tweets <- twListToDF(searchTwitter(searchKey, n = numTweets, lang = "en"))
# windows running write.csv(twListToDF(tweets), "C:\\Users\\socce\\Documents\\TamuHackTest\\tweets.csv") #an example of a file extension of the folder in which you want to save the .csv file.
write.csv(tweets, "tweets.csv") #an example of a file extension of the folder in which you want to save the .csv file.
}
