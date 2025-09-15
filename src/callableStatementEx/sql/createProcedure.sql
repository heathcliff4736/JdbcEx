USE bookmarketdb;

DELIMITER $$
CREATE PROCEDURE SP_MEMBER_INSERT(
    IN V_USERID VARCHAR(20),
    IN V_PWD VARCHAR(20),
    IN V_EMAIL VARCHAR(50),
    IN V_HP VARCHAR(20),
    OUT RTN_CODE INT
)
BEGIN
    DECLARE v_count int;

SELECT COUNT(m_seq) into v_count FROM TB_MEMBER WHERE M_USERID = V_USERID;
IF v_count > 0 THEN
        SET RTN_CODE = 100;
ELSE
        INSERT INTO TB_MEMBER (M_USERID, M_PWD, M_EMAIL, M_HP)
            VALUES (V_USERID, V_PWD, V_EMAIL, V_HP);
        SET RTN_CODE = 200;
end if;
COMMIT;
END $$
DELIMITER ;



delimiter @@
create procedure SP_MEMBER_LIST()
begin
    SELECT * FROM TB_MEMBER;
end @@
delimiter ;



delimiter @@
create procedure SP_MEMBER_Search(IN userid VARCHAR(20))
begin
    SELECT * FROM TB_MEMBER WHERE m_userid = userid;
end @@
delimiter ;



delimiter $$
create procedure SP_MEMBER_INFO_UPDATE(IN userid VARCHAR(20),
                                       IN menuNumber int,
                                       IN updateText VARCHAR(50))
begin
    CASE
        WHEN menuNumber = 1 THEN
            UPDATE TB_MEMBER SET m_pwd = updateText WHERE m_userid = userid;
        WHEN menuNumber = 2 THEN
            UPDATE TB_MEMBER SET m_email = updateText WHERE m_userid = userid;
        WHEN menuNumber = 3 THEN
            UPDATE TB_MEMBER SET m_hp = updateText WHERE m_userid = userid;
        END CASE;
end $$
delimiter ;



delimiter $$
create procedure SP_MEMBER_DELETE(IN userid VARCHAR(20))
begin
    DELETE FROM TB_MEMBER WHERE m_userid = userid;
end $$
delimiter ;



