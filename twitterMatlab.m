consumerkey = %consumer Key (From Twitter)
consumersecret =  %consumer Secret key (From Twitter)
accesstoken = %access token (From Twitter)
accesstokensecret = %secret access token (From Twitter)
c = twitter(consumerkey,consumersecret,accesstoken,accesstokensecret);
searchThing='bakedpotatoes OR #potatoflower OR #nutforpotatoes OR #potatoes4lyf';
lib=query(100,searchThing,c);
% always running
while(c.StatusCode == 'OK')
    changed=false;
    % queries 100 tweets
    temp=query(100,searchThing,c);
    if(~isempty(lib))
        timeArray=lib(:).time;
        for i=length(temp):-1:1
            % checks if the new data is in the library
            if(~ismember(temp(i).time,timeArray))
                lib(length(lib)+1).time=temp(i).time;
                lib(length(lib)).location=temp(i).location;
                changed=true;
            end
        end
    else
        % library is not empty
        changed=true;
        for i=length(temp):-1:1
                lib(length(lib)+1).time=temp(i).time;
                lib(length(lib)).location=temp(i).location;
        end
    end
    output = struct('time','','longitude',0,'latitude',0);
    for i = 1:length(lib)
        output(i).time=lib(i).time(5:19);
        output(i).longitude=lib(i).location(1).coordinates(1);
        output(i).latitude=lib(i).location(1).coordinates(2);
    end
    struct2csv(output,'library.csv');
    % change the length of the pause depending on if there is a new change to the
    % library 
    if(changed)
        pause(10);
    else
        pause(20);
    end
end

function list = query(num,searchTerm,magic)
%parameters.count = num;
D=search(magic,searchTerm,'count',num);
statuss = D.Body.Data.statuses(:);
tempList(1).time='';
tempList(1).location=[];
for i = length(statuss):-1:1
    if(~isempty(statuss{i}.geo))
        tempList(length(tempList)+1).time=statuss{i}.created_at;
        tempList(length(tempList)).location=statuss{i}.coordinates;
    end
end
       tempList(1)=[];
       list=tempList;
end
