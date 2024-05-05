function
	sys.wait(30000)
	local taskname="yzg7Task"
	log.info(taskname,"start")
	local netid=1
	local netmsg ="UART_DATA_TO_NET"..netid
	local netr =nil
	local uartid =2
	local uartmsg ="UART"..uartid.."_NEED_SEND"
	local uartr =nil
	local nowsta,oldsta=nil,nil
	local inid =1
	local outid=1
	
	pronet.PronetStopProNetRecive(1)
	while true do
		netr = pronet.PronetGetReciveChacheAdnDel(netid)
		if netr then 
			log.info(taskname,"get net data=",netr)
			if netr =="on" then 
				per.SetDoOut(outid,1)
			elseif netr =="off" then
				per.SetDoOut(outid,0)
			elseif string.find(netr, "ctron") then 
				local separator = "," 
                    local t = {}
                    for str in string.gmatch(netr, "([^" .. separator .. "]+)") do
                        table.insert(t, str)
                    end
                    local length=#t
                    if length>=2 then 
                    local n=t[2]                   
                            for i=1, n,1 do                        
                                per.SetDoOut(outid,1)
                                sys.wait(500)
                                per.SetDoOut(outid,0) 
                                sys.wait(500) 
                            end 
                        
                    end 
			else
				prouart.ProuartSetSendChace(uartid,netr) 
				sys.publish(uartmsg)
			end 
		end 
		
		nowsta = per.GetDiInputById(inid) 
		if nowsta ~= oldsta  then 
			oldsta = nowsta
			if nowsta ==0 then 
				per.SetDoOut(outid,1)
				sys.wait(5000)
				per.SetDoOut(outid,0)
				if pronet.PronetGetNetState(netid) == 1 then 
					pronet.PronetInsertSendChache(netid,"in1 trigger")
					sys.publish(netmsg)
				end
			end
		end 

		sys.wait(100)
	end 
end