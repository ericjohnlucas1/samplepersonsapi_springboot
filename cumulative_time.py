import requests
import time

base_url = "http://localhost:8080"


def person_controller_runthrough():
    resp = requests.post(base_url + "/persons", json = {"name":"arbitraryperson", "age":"10"})
    resp2 = requests.get(base_url + "/persons/" + str(resp.json()["id"]))
    resp = requests.get(base_url + "/persons?youngerthan=100")


controller_endpoint_runthroughs = {
    "person_controller_trial1": person_controller_runthrough,
    "person_controller_trial2": person_controller_runthrough,
    "person_controller_trial3": person_controller_runthrough,
    "person_controller_trial4": person_controller_runthrough
}



controller_time_spend = []
for cont, runfunc in controller_endpoint_runthroughs.items():
    start = time.time()
    runfunc()
    end = time.time()
    controller_time_spend.append([cont, end-start])


controller_time_spend = sorted(controller_time_spend, key=lambda x: -x[1])

total_time_spend = 0
for i in controller_time_spend:
    total_time_spend += i[1]


for i in controller_time_spend:
    print("%s   %s%%" % (i[0], 100*i[1]/total_time_spend))
    

