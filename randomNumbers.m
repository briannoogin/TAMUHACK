n = 10000;

output = struct('time','','longitude',0,'latitude',0);
    for i = 1:n
        output(i).time='Sun Jan 28 09:36:59 +0000 2018';
        output(i).longitude=(((randn+.5)/5 * (-96.343458+96.336334) -96.336334)); 
        output(i).latitude=(((randn+.5)/5 * (30.61806-30.612401) + 30.612401)); 
    end
    struct2csv(output,'simulatedTweets.csv');