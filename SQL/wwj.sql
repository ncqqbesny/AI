SELECT  GID,cmd_no,m_id,user_id,status,send_time,send_cmd,send_url,cmd_desc,rev_cmd,rev_url,rev_time,remark,LAST_TIME,CREATE_TIME  FROM t_dtu_cmd     WHERE (send_cmd IS NOT NULL AND status = ? AND send_url = ? AND rev_url = ?) ORDER BY CREATE_TIME ASC

DELETE FROM t_dtu_cmd

SELECT  t.send_cmd,t.rev_cmd FROM t_dtu_cmd  t;

DELETE
FROM t_device 
WHERE DEVICESN IS NULL;

DELETE
FROM t_device_extend  
WHERE not EXISTS(SELECT 1 FROM t_device d WHERE DEVICEGID=d.gid)

SELECT * FROM t_device



