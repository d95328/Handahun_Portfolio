import numpy as np
import cv2
import os
import imutils
import argparse
from imutils import paths
import requests
import RPi.GPIO as GPIO
from time import sleep
import time
from picamera import PiCamera
import socket, threading
import select
import json

path = '/home/pi/iot_image/'
images = []

button_pin = 17
green = 13
red = 19
blue = 26
Freq = 100

GPIO.setmode(GPIO.BCM)
GPIO.setup(button_pin, GPIO.IN)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(red, GPIO.OUT)
GPIO.setup(green, GPIO.OUT)
GPIO.setup(blue, GPIO.OUT)

RED = GPIO.PWM(red,Freq)
GREEN = GPIO.PWM(green, Freq)
BLUE = GPIO.PWM(blue, Freq)

p = GPIO.PWM(18, 50)
p.start(0)

camera = PiCamera()
camera.resolution = (2592, 1944)

pressed = 0
motor_status = 0

capture_time = 0

DIM=(2592, 1944)
K=np.array([[1202.724908291095, 0.0, 1287.6269100685556], [0.0, 1205.0556858275993, 959.0790869803135], [0.0, 0.0, 1.0]])
D=np.array([[-0.0857155911440568], [0.07886898769246035], [-0.13907268724598443], [0.06568158474939788]])

user_name = ""

def undistort(img_path, img_no, balance=0.0, dim2=None, dim3=None):
    img = cv2.imread(img_path)
    dim1 = img.shape[:2][::-1]  #dim1 is the dimension of input image to un-distort
    assert dim1[0]/dim1[1] == DIM[0]/DIM[1], "Image to undistort needs to have same aspect ratio as the ones used in calibration"
    if not dim2:
        dim2 = dim1
    if not dim3:
        dim3 = dim1
    scaled_K = K * dim1[0] / DIM[0]  # The values of K is to scale with image dimension.
    scaled_K[2][2] = 1.0  # Except that K[2][2] is always 1.0
    # This is how scaled_K, dim2 and balance are used to determine the final K used to un-distort image. OpenCV document failed to make this clear!
    new_K = cv2.fisheye.estimateNewCameraMatrixForUndistortRectify(scaled_K, D, dim2, np.eye(3), balance=balance)
    map1, map2 = cv2.fisheye.initUndistortRectifyMap(scaled_K, D, np.eye(3), new_K, dim3, cv2.CV_16SC2)
    undistorted_img = cv2.remap(img, map1, map2, interpolation=cv2.INTER_LINEAR, borderMode=cv2.BORDER_CONSTANT)
    cv2.imwrite('/home/pi/iot_image/undistort_'+str(capture_time)+'_'+str(img_no)+'.jpg', undistorted_img)

try:
    # android connect
    RED.start(100)
    GREEN.start(1)
    BLUE.start(1)
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind(('192.168.0.9', 9990))
    print("socket ready.")
    server_socket.listen()
    client_socket, addr = server_socket.accept()
    print("socket ok.")
    
    #save the user_name
    while True:
        data = client_socket.recv(1024)
        msg = data.decode("euc-kr")
        print("connect !")
        if msg[0:1] == "1":
            client_socket.send(bytes("1","euc-kr")) 
            user_name = msg[1:]
            print(user_name)
            RED.ChangeDutyCycle(1)
            GREEN.ChangeDutyCycle(100)
            break
    #user_info text file make
    
    while True:
        a = GPIO.input(button_pin)        
        if a == 1:
            RED.ChangeDutyCycle(0)
            GREEN.ChangeDutyCycle(0)
            BLUE.ChangeDutyCycle(100)
            client_socket.send(bytes("2","euc-kr")) 
            if pressed == 0:
                pressed = 1
                capture_time = time.time()
                camera.start_preview()
                p.ChangeDutyCycle(2.5)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_1.jpg')
                sleep(2)
                p.ChangeDutyCycle(4.75)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_2.jpg')
                sleep(2)
                p.ChangeDutyCycle(7)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_3.jpg')
                sleep(2)
                p.ChangeDutyCycle(9.2)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_4.jpg')
                sleep(2)
                p.ChangeDutyCycle(11)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_5.jpg')
                sleep(2)
                p.ChangeDutyCycle(14)
                sleep(0.2)
                p.ChangeDutyCycle(0)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_6.jpg')
                sleep(2)
                p.ChangeDutyCycle(14)
                sleep(0.85)
                p.ChangeDutyCycle(0)
                sleep(2)
                camera.capture('/home/pi/iot_image/capture_'+str(capture_time)+'_7.jpg')
                sleep(2)
                p.ChangeDutyCycle(1)
                sleep(0.5)
                p.ChangeDutyCycle(2.5)
                sleep(2)
                camera.stop_preview()
                camera.close()
                print("[1] - camera capture complete !!")
                client_socket.send(bytes("5","euc-kr"))
                
                sleep(1)
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_1.jpg',1)
                print("[2] - undistort - 1 ... ok")
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_2.jpg',2)
                print("[2] - undistort - 2 ... ok")
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_3.jpg',3)
                print("[2] - undistort - 3 ... ok")
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_4.jpg',4)
                print("[2] - undistort - 4 ... ok")
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_5.jpg',5)
                print("[2] - undistort - 5 ... ok")
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_6.jpg',6)
                print("[2] - undistort - 6 ... ok")
                undistort('/home/pi/iot_image/capture_'+str(capture_time)+'_7.jpg',7)
                print("[2] - undistort - 7 ... ok")
                
                print("[2] - undistort complete !!")
                client_socket.send(bytes("6","euc-kr"))
                for root, directories, files in os.walk(path):   
                    for file in files:
                        if 'undistort_'+str(capture_time) in file:
                            img_input =cv2.imread(os.path.join(root, file))

                            images.append(img_input)


                stitcher = cv2.Stitcher.create(cv2.Stitcher_PANORAMA)
                status, stitched = stitcher.stitch(images)
                time.sleep(3)

                if status != cv2.Stitcher_OK:
                    print("Can't stitch images, error code = %d" % status)
                    exit(-1)

                ##cv.imwrite('/home/pi/imageTest/20.06.12_test_2/result.jpg', pano)
                print("[3] - stitching ... ok")
                client_socket.send(bytes("7","euc-kr"))

                if status == 0:
                    print("[INFO] cropping...")
                    stitched = cv2.copyMakeBorder(stitched, 10, 10, 10, 10, cv2.BORDER_CONSTANT, (0, 0, 0))
                    gray = cv2.cvtColor(stitched, cv2.COLOR_BGR2GRAY)
                    thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY)[1]


                    cnts = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL,
                    cv2.CHAIN_APPROX_SIMPLE)
                    cnts = imutils.grab_contours(cnts)
                    c = max(cnts, key=cv2.contourArea)

                    mask = np.zeros(thresh.shape, dtype="uint8")
                    (x, y, w, h) = cv2.boundingRect(c)
                    cv2.rectangle(mask, (x, y), (x + w, y + h), 255, -1)

                    minRect = mask.copy()
                    sub = mask.copy()

                    while cv2.countNonZero(sub) > 0:
                        minRect = cv2.erode(minRect, None)
                        sub = cv2.subtract(minRect, thresh)

                    cnts = cv2.findContours(minRect.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
                    cnts = imutils.grab_contours(cnts)
                    c = max(cnts, key=cv2.contourArea)
                    (x, y, w, h) = cv2.boundingRect(c)
                    stitched = stitched[y:y + h, x:x + w]            
                            
                            
                    cv2.imwrite(path + str(capture_time)+'_result.jpg', stitched)
                else:
                    print("[INFO] image stitching failed ({})".format(status))
                    
                print("complete!")
                client_socket.send(bytes("8","euc-kr"))
                client_socket.send(bytes(str(capture_time)+'_result.jpg',"euc-kr"))
                server_socket.close()
                sleep(1)
                file = open(path + str(capture_time)+'_result.jpg','rb')
                upload_file = {'image' : file }
                u_name = {'userid': user_name }
                res = requests.post('http://112.164.58.12/moment/iotData', files = upload_file, data = u_name )
                #client_socket.send(bytes("3","euc-kr"))
                print("post complete!")   
                sleep(2)
                RED.ChangeDutyCycle(100)
                GREEN.ChangeDutyCycle(1)
                BLUE.ChangeDutyCycle(1)
        else:            
            pressed = 0
        
except:
    client_socket.send(bytes("4","euc-kr"))
    pass
finally:
    RED.ChangeDutyCycle(100)
    GREEN.ChangeDutyCycle(1)
    BLUE.ChangeDutyCycle(1)
    server_socket.close();
    GPIO.cleanup()   




