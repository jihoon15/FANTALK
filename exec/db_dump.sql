-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: timeroom
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `created_at` datetime(6) DEFAULT NULL,
  `hits` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `community_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5jf7uaerwnmm307kq5yv7xv30` (`community_id`),
  KEY `FKbc2qerk3l47javnl2yvn51uoi` (`user_id`),
  CONSTRAINT `FK5jf7uaerwnmm307kq5yv7xv30` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`),
  CONSTRAINT `FKbc2qerk3l47javnl2yvn51uoi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'해피엔딩이어서 너무 좋더라..\n다같이 마녀2도 봅시다!!','2022-02-15 22:48:29.710000',25,'그해우리는 마지막화 봤어??','2022-02-15 23:12:39.017000',2,1),(2,'여기저기 카페도 다니고 운전도 하는데 보면서 진짜 힐링됨!!!! 다들 꼭 보시길 :)','2022-02-15 22:50:15.736000',25,'디스커버리채널코리아에 김다미 유툽 영상있어요~','2022-02-15 23:11:44.102000',2,1),(3,'찍어줘!','2022-02-15 23:01:47.394000',13,'그 해 우리는 시즌2 존버합니다..','2022-02-16 00:00:47.750000',2,2),(4,'여러분도 다시 정주행 ㄱㄱ','2022-02-15 23:02:53.283000',10,'다미님 보려고 이태원 클라스 정주행중입니다..','2022-02-15 23:35:43.102000',2,2),(5,'너무 어렵네요.. 둘 다 매력있었는데..','2022-02-15 23:05:59.932000',17,'마녀 김다미 vs 그해우리는 김다미','2022-02-16 13:52:00.163000',2,1),(6,'500만 달리자~~~~','2022-02-15 23:07:49.128000',12,'다미님 인스타 400만 달성!!!!','2022-02-16 13:52:06.552000',2,2),(7,'웹툰도 봐볼까용','2022-02-15 23:31:09.023000',12,'그해 우리는 웹툰은 어떤가요?','2022-02-15 23:35:17.893000',2,2),(8,'너무 좋네요 구성 알차다','2022-02-15 23:32:09.816000',12,'그 해 우리는 OST 앨범 도착~~','2022-02-15 23:35:34.793000',2,6),(9,'긴 말 안합니다','2022-02-15 23:32:52.639000',18,'여러분 \'잠적\' 정주행하세요','2022-02-15 23:35:40.339000',2,6),(10,'스브스 그해우리는 영상으로 1일 1다미 하실 분 모집합니다~~~','2022-02-15 23:33:09.102000',11,'1일 1다미 하실 분 모집합니다~~','2022-02-15 23:35:30.903000',2,1),(11,'나였어도 연수보러 도서관 매일 갔을듯..','2022-02-15 23:34:26.757000',12,'웅이는 사실 연수 때문에 독서왕이 된 게 아닐까?','2022-02-15 23:35:22.575000',2,2),(12,'저 노래만 들으면 자꾸 생각나네요ㅋㅋㅋㅋㅋㅋ 여러분도 그러신가요??','2022-02-15 23:34:31.242000',12,'담다디 담다디 담다미 담~~','2022-02-15 23:35:26.701000',2,1),(13,'봐도 봐도 너무 재밌네요ㅎㅎㅎㅎ','2022-02-15 23:35:48.472000',13,'그해우리는 정주행중~~~','2022-02-15 23:59:45.705000',2,1),(14,'진짜 인정하기 싫지만 잘 어울리네요ㅋㅋㅋㅋㅋㅋㅋㅋ그래서 드라마에서도 캐미가 좋았던 듯!!','2022-02-15 23:36:57.868000',9,'코스모폴리탄 김다미 최우식 화보 보신 분ㅎㅎㅎ','2022-02-16 00:00:10.231000',2,1),(15,'디스커버리 유툽에 있는 김다미 브이로그 같은 영상 진짜 재밌네요ㅋㅋㅋㅋㅋ 그냥 바로 힐링~~','2022-02-15 23:38:06.261000',6,'김다미 유툽으로 힐링중~~~','2022-02-16 00:00:05.388000',2,1),(16,'평생 소장!','2022-02-15 23:38:15.510000',10,'저도 OST 받았습니다~~!','2022-02-16 13:29:40.227000',2,2),(17,'최근에 이미지가 비슷해서 그런지 많이 비교가 되네요ㅋㅋㅋㅋㅋㅋ 여러분은 다 김다미겠죠??','2022-02-15 23:38:51.297000',12,'조이현 vs 김다미','2022-02-16 10:48:46.914000',2,1),(18,'연기 디테일이 와..','2022-02-15 23:39:23.863000',8,'이클보고 김다미 팬됐습니다.','2022-02-15 23:59:55.991000',2,4),(19,'여름방학에 정유미 최우식도 캐미 좋았는데 김다미 최우식도 캐미 좋을듯!!!!','2022-02-15 23:39:46.328000',10,'tvn 여름방학에 김다미 최우식 한번 가나요??','2022-02-15 23:59:51.445000',2,1),(20,'궁금합니다.','2022-02-15 23:39:47.107000',9,'잠적 투어 정리된 곳 있나요?','2022-02-16 00:00:00.746000',2,4),(21,'나 더는 못기다려~~','2022-02-15 23:41:25.833000',9,'마녀2 개봉일이 언제죠?','2022-02-15 23:59:21.293000',2,2),(22,'너무 심심해서 한번 분석해봤다\n\n이태원 클라쓰 - 2019.8월~2020.3월(8개월 촬영)\n\n-------------휴식 기간(인텁 및 온라인 팬미팅) 4개월 -------------\n\n소울메이트 - 2020.8월~2020.11월(4개월 촬영)\n\n마녀2 - 2020.12월~2021.3월(4개월 촬영) - 특출인거 치고 다미도 촬영 많이 하는 것 같았음\n\n그 해 우리는 - 2021.3월 중순 캐스팅 소식, 7월 초 대본리딩, 7월 말 촬영 시작, 12월 말 촬영종료(5개월 촬영)\n\n그 이후 휴식 기간 예상 : 2개월\n\n결론 - 3월 캐스팅 소식 존버','2022-02-15 23:50:21.887000',13,'다미의 패턴을 araboja','2022-02-16 00:00:21.245000',2,2),(23,'그해우리는이 모티브로한 다큐도 꿀잼이네요!! 꼭 다들 보시길~~','2022-02-15 23:50:41.574000',12,'그해우리는 모티브 다큐도 재밌네요!','2022-02-15 23:58:59.288000',2,1),(24,'보통 타이틀하나만 기억에 남는 느낌인데\n\n에피소드별로 컨셉잡고 곡을 짜서 그런가 곡이 드라마에 잘 어울리고 퀄리티가 좋은거 같아..','2022-02-15 23:50:56.456000',15,'그해울 ost 진짜 맛집이네..','2022-02-16 13:51:51.831000',2,2),(25,'1분 1초가 아까워서 두부먹는 장면까지만 계속 돌려보고 있어 본 거 보고 또 보고 하는데 질리지가 않아\n  \n이거 나만 그런거 아니지?\n','2022-02-15 23:51:18.972000',18,'다미 잠적보는데','2022-02-16 23:21:21.716000',2,2),(26,'진짜 김다미라는 이름 예쁜듯','2022-02-15 23:53:52.340000',18,'다미다미~~ dami~~ dami','2022-02-16 23:21:19.050000',2,1),(27,'ㅋ','2022-02-15 23:54:15.564000',14,'그런데 말입니다','2022-02-16 15:05:29.788000',2,3),(28,'진짜 빨리 김다미 차기작 나왔으면!!!!!!!!!!','2022-02-15 23:54:34.102000',17,'빨리 김다미 차기작 나왔으면....','2022-02-16 23:21:25.831000',2,1),(29,'역시 존버는 승리한다!!! 끝나고 바로 후기 남길게요ㅋㅋㅋㅋㅋ','2022-02-15 23:55:10.703000',19,'김다미 FANTALK 팬미팅 예매 성공했습니다!!!!','2022-02-17 10:35:39.443000',2,1),(30,'언제함??','2022-02-15 23:55:12.912000',19,'근데 팬 미팅','2022-02-17 10:35:41.857000',2,3),(31,'현기증 난다!!!!!!!!!!!!!!!!!!!','2022-02-15 23:56:12.872000',19,'진짜 팬미팅 빨리 했으면!!!','2022-02-17 10:52:58.975000',2,1),(33,'자주 오는 술집인가 봄 \n주인장이랑 친해보이던데..','2022-02-15 23:56:31.845000',23,'홍대 OO에서 다미좌 봄','2022-02-16 23:21:30.130000',2,3),(34,'은 거짓말~~\n\n힝! 속았지???','2022-02-15 23:58:56.897000',41,'얼마 전에 김다미 봄!!!','2022-02-17 10:15:17.830000',2,1),(35,'오랜만이네 ㅎㅎ 나중에 또 보자 ^^','2022-02-16 13:55:57.883000',15,'다미야 안녕?!!','2022-02-17 10:52:44.700000',2,5),(36,'아아','2022-02-16 16:10:49.627000',11,'공유님 죄송하지만 테스트좀','2022-02-17 10:53:07.564000',6,3),(37,'연수야ㅑㅑㅑ','2022-02-16 23:22:59.917000',9,'안녕','2022-02-17 13:29:10.165000',2,10);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `created_at` datetime(6) DEFAULT NULL,
  `article_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yx0uphgjc6ik6hb82kkw501y` (`article_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK5yx0uphgjc6ik6hb82kkw501y` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'스포하네..','2022-02-15 22:51:33.296000',1,3),(2,'이제 2화봤는데 스포해주셔서 감사합니다..','2022-02-15 22:51:57.835000',1,2),(3,'https://www.youtube.com/watch?v=5Sw8bhsJ_PE','2022-02-15 22:53:24.361000',2,3),(4,'네~ 잘 봤습니다~','2022-02-15 22:56:32.434000',2,2),(5,'찍어줘!','2022-02-15 23:04:04.755000',3,1),(6,'마녀부터 보고 오시죠!','2022-02-15 23:04:26.932000',4,1),(7,'ㅉㅇㅈ!','2022-02-15 23:06:06.755000',3,3),(8,'닥전','2022-02-15 23:06:44.629000',5,3),(9,'ㄷㅎ','2022-02-15 23:08:54.927000',5,2),(10,'오 1000만도 갈듯하네요~~~','2022-02-15 23:29:48.628000',6,1),(11,'그걸 모름?','2022-02-15 23:55:51.661000',30,1),(12,'이 분 신고합니다.','2022-02-15 23:57:03.233000',33,1),(13,'엣헴','2022-02-16 10:56:19.473000',34,1),(14,'ee','2022-02-16 13:29:40.040000',16,3),(15,'.','2022-02-16 13:33:47.940000',34,3),(16,'ㅇㅈㅇㅈ','2022-02-16 13:42:55.710000',28,3),(18,'오? 진짜로??','2022-02-16 13:57:23.375000',34,5),(19,'ㅋㅋ','2022-02-16 16:08:51.488000',34,3),(20,'ㅁㄴㅇ','2022-02-16 16:10:54.424000',36,3);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ggi0mfnbrejia9lxku7voffc9` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
INSERT INTO `community` VALUES (1,'2022-02-15 22:42:02.011000','아이유','어른말고아이유','2022-02-15 22:42:02.011000'),(2,'2022-02-15 22:42:22.372000','김다미','담다디다미','2022-02-15 22:42:22.372000'),(3,'2022-02-15 22:43:24.797000','최우식','우시기뽀시기','2022-02-15 22:43:24.797000'),(4,'2022-02-15 22:44:33.326000','김태리','태리야끼','2022-02-15 22:44:33.326000'),(5,'2022-02-15 22:45:52.842000','박보영','사랑아보영해','2022-02-15 22:45:52.842000'),(6,'2022-02-15 22:46:26.728000','공유','공유폴더','2022-02-15 22:46:26.728000');
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enter_code`
--

DROP TABLE IF EXISTS `enter_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enter_code` (
  `id` varchar(255) NOT NULL,
  `checked` bit(1) NOT NULL,
  `meeting_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4kb2xh8mmvll5w67a6661eppr` (`meeting_id`),
  CONSTRAINT `FK4kb2xh8mmvll5w67a6661eppr` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enter_code`
--

LOCK TABLES `enter_code` WRITE;
/*!40000 ALTER TABLE `enter_code` DISABLE KEYS */;
INSERT INTO `enter_code` VALUES ('4kM7fgfGzi4X',_binary '\0',3),('9phmYFGq4bSw',_binary '\0',3),('B6QvEoWCRwlA',_binary '\0',3),('d1MVRX14i15h',_binary '\0',3),('DOszXpSISbsP',_binary '\0',3),('DvAdIK2GGhSO',_binary '',3),('ebqUOiETcYvE',_binary '\0',3),('emQilWooK9NJ',_binary '\0',3),('IJ4itEQSNz0F',_binary '\0',3),('iRF3bdFPRdNX',_binary '\0',3),('m6oqBAH6StAb',_binary '\0',3),('ntr9yuXfU994',_binary '\0',3),('pVsLNVSVH97K',_binary '\0',3),('r9fJDv9QVW5z',_binary '\0',3),('RbPMUf8m1B7v',_binary '\0',3),('RHCkQvCWxup1',_binary '\0',3),('tLngpJBrUCra',_binary '\0',3),('VoruF0IKz3dy',_binary '\0',3),('XCnPCfbB9nSm',_binary '\0',3),('yjA1FHWqOa9y',_binary '\0',3),('ykBgb2L0VR3I',_binary '\0',3);
/*!40000 ALTER TABLE `enter_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) DEFAULT NULL,
  `is_in_manager` bit(1) DEFAULT NULL,
  `max_user` int NOT NULL,
  `open_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmuhswkfclydkvuemoe1tsgu0n` (`user_id`),
  CONSTRAINT `FKmuhswkfclydkvuemoe1tsgu0n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
INSERT INTO `meeting` VALUES (3,_binary '\0',_binary '\0',20,'2022-02-17 02:30:00.000000','최우식 팬미팅',5);
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hong267@naver.com','김홍중','$2a$10$/KtH4EeaD.msmhIYaARKFucVs7rP9yFP1qY4262rRJvF3RTAy0lq6','ROLE_MANAGER','timeroom1'),(2,'khyunchoi@naver.com','최강현','$2a$10$w49fu2JU0iI33Ifoawp1IOjtngefy9.CTNqnoPTWwUPlI5WbKjgnG','ROLE_MANAGER','timeroom2'),(3,'dhwlgns7028@naver.com','오지훈','$2a$10$4t3Kd9.Sw/DzAkZOKZJn0u7Gqg/BNuLR/ZbjFTA.L7ijNjf6o4Ho.','ROLE_MANAGER','timeroom3'),(4,'paradigmshifter@naver.com','박한빈','$2a$10$94iJtCXBvY.ZmuptexI4xOQHmhdmls8zaOv.OUoykWFCzcSX/rsFi','ROLE_MANAGER','timeroom4'),(5,'woo123@naver.com','최우식','$2a$10$WIEdcaJIXRNI6F3emjg33eKWhRA1yUqV336Kj4xRZKeIjjb6ehRhG','ROLE_MANAGER','managerUser1'),(6,'ssafy123@ssafy.com','김싸피','$2a$10$vWn6IGy0P3zXQtMpCqRlcOOn2cyknyShbF.G.lmXBhTnyrqV01he6','ROLE_USER','user1'),(7,'khyunchoi@hanmail.net','최강','$2a$10$zoxNH4xzG8mHFFAOrJAVResB3B.J9iWI0tBI7Rw30VTXZRA9ENu1K','ROLE_USER','khyunchoi'),(8,'khyunchoi@gmail.com','최최최','$2a$10$g6A2GP7u92N3wj5x6c9nOOHlu3hNkL8GJsVDfaNVftSXAsichI1J6','ROLE_USER','khyunchoitest'),(9,'ads','ads','$2a$10$wAePg6DnjcMhrfdfo6m2cOhGE/suei5mgMRh1uobuZO2RL7wTLCp6','ROLE_USER','asd'),(10,'kkkk@naver.com','국연수','$2a$10$toQaBrlTeg/BdNMI3EFwbuv3GpJP9vg9VBF4hvP7mwFNpT8mOnVfy','ROLE_USER','hi'),(11,'oj@gmail.com','하이','$2a$10$Y0Qokw9IsFRoobhYFtOYG.54b9czpCqEkucC0m6K27EQAJMLsq1Pa','ROLE_USER','oi'),(12,'oj@gmail.com','hihi','$2a$10$ie2iYCjJhfIIbkUgK0S0A.Q3AMHqs1NdmZZMFsGOXFtWEIjvWfZOO','ROLE_USER','iufan');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-17 14:55:06
