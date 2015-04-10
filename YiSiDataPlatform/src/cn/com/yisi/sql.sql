select count(*) from User where user='admin' and pwd='123';
SELECT Name FROM Master..SysDatabases ORDER BY Name
 SELECT Name FROM SysObjects Where XType='U' ORDER BY Name
 select * from VWB_WEIGHT ;
 select top 7 convert(char(10),weightdate,120),sum(suttle) from vwb_weight where id>10 group by convert(char(10),weightdate,120) order by convert(char(10),weightdate,120) desc;
 select convert(char(10),weightdate,0) as wtime,sum(suttle) as 皮重 from vwb_weight group by convert(char(10),weightdate,0);
 select top 10 suttle,cars,supply,drivers,material,materialkind,weightdate,lightdate from VWB_WEIGHT;
 select top 7 convert(char(10),weightdate,120) as time,sum(suttle) as weight from vwb_weight where type=0 group by convert(char(10),weightdate,120) order by convert(char(10),weightdate,120) desc;
 select top 14 convert(char(10),weightdate,120) as time,sum(suttle) as weight,type from vwb_weight where type=0 or type=3 group by convert(char(10),weightdate,120),type order by convert(char(10),weightdate,120) desc;