drop database if exists adv22;
create database if not exists adv22 character set utf8mb4 collate utf8mb4_general_ci;
use adv22;
create table dept(
    dept_id integer(3) primary key auto_increment,
    dept_name varchar(100) not null
);
create table esq_user(
    esq_id varchar(6) primary key,
    dept_id integer(3) not null,
    user_name varchar(100) not null,
    password varchar(128) not null,
    foreign key(dept_id) references dept(dept_id)
);
create table question_type(
    question_type_id integer(2) primary key auto_increment,
    question_type varchar(10) not null
);
create table enquete_state(
    enquete_state_id integer(2) primary key auto_increment,
    enquete_state varchar(10) not null
);
create table enquete(
    enquete_id integer(5) primary key auto_increment,
    enquete_name varchar(100),
    enquete_state_id integer(2) not null,
    create_user_id varchar(6) not null,
    create_date date not null,
    start_date date,
    finish_date date,
    enquete_subtext varchar(200),
    version integer(18) not null,
    foreign key(enquete_state_id) references enquete_state(enquete_state_id),
    foreign key(create_user_id) references esq_user(esq_id)
);
create table question(
    question_id integer(7) primary key auto_increment,
    enquete_id integer(5) not null,
    question_number integer(3) check(question_number > 0),
    question_type_id integer(2),
    require_flag integer(1) not null check(
        require_flag = 0
        or require_flag = 1
    ),
    question_text varchar(100),
    question_subtext varchar(200),
    version integer(18) not null,
    foreign key(enquete_id) references enquete(enquete_id),
    foreign key(question_type_id) references question_type(question_type_id)
);
create table choice(
    choice_id integer(7) primary key auto_increment,
    question_id integer(7) not null,
    choice_number integer(3) check(choice_number > 0),
    choice_text varchar(100),
    version integer(18) not null,
    foreign key(question_id) references question(question_id)
);
create table enquete_dept(
    enquete_id integer(5),
    dept_id integer(3),
    foreign key(enquete_id) references enquete(enquete_id),
    foreign key(dept_id) references dept(dept_id),
    primary key auto_increment(enquete_id, dept_id)
);
create table enquete_admin_user(
    enquete_id integer(5) not null,
    esq_id varchar(6) not null,
    delete_flag integer(1) not null check(
        delete_flag = 0
        or delete_flag = 1
    ),
    foreign key(enquete_id) references enquete(enquete_id),
    foreign key(esq_id) references esq_user(esq_id),
    primary key auto_increment(enquete_id, esq_id)
);
create table enquete_answer(
    enquete_answer_id integer(8) primary key auto_increment,
    enquete_id integer(5) not null,
    esq_id varchar(6) not null,
    answer_date date,
    foreign key(enquete_id) references enquete(enquete_id),
    foreign key(esq_id) references esq_user(esq_id)
);
create table question_answer(
    question_answer_id integer(10) primary key auto_increment,
    enquete_answer_id integer(8) not null,
    question_id integer(7) not null,
    answer_text varchar(200),
    foreign key(enquete_answer_id) references enquete_answer(enquete_answer_id),
    foreign key(question_id) references question(question_id)
);
create table choice_answer(
    choice_answer_id integer(10) primary key auto_increment,
    question_answer_id integer(10) not null,
    choice_id integer(7) not null,
    foreign key(choice_id) references choice(choice_id),
    foreign key(question_answer_id) references question_answer(question_answer_id)
);
create index question_index1 on question(enquete_id, question_number);
create index choice_index1 on choice(question_id, choice_number);
create index enquete_answer_index1 on enquete_answer(enquete_id, esq_id);
create index question_answer_index1 on question_answer(question_answer_id, question_id);
create index choice_answer_index1 on choice_answer(question_answer_id, choice_id);
start transaction;
insert into question_type (question_type_id, question_type)
values (1, '単一選択');
insert into question_type (question_type_id, question_type)
values (2, '複数選択');
insert into question_type (question_type_id, question_type)
values (3, '自由記述');
insert into enquete_state (enquete_state_id, enquete_state)
values (1, '一時保存');
insert into enquete_state (enquete_state_id, enquete_state)
values (2, '作成完了');
insert into enquete_state (enquete_state_id, enquete_state)
values (3, '回答受付');
insert into enquete_state (enquete_state_id, enquete_state)
values (4, '受付終了');
insert into dept (dept_id, dept_name)
values (1, '経営企画室');
insert into dept (dept_id, dept_name)
values (2, 'コーポレート本部');
insert into dept (dept_id, dept_name)
values (3, 'Xイノベーション本部');
insert into dept (dept_id, dept_name)
values (4, '事業統括推進室');
insert into dept (dept_id, dept_name)
values (5, '金融ソリューション事業部');
insert into dept (dept_id, dept_name)
values (6, 'ビジネスソリューションセグメント直轄');
insert into dept (dept_id, dept_name)
values (7, 'グループ経営ソリューション事業部');
insert into dept (dept_id, dept_name)
values (8, 'HCM事業部');
insert into dept (dept_id, dept_name)
values (9, '製造ソリューション事業部');
insert into dept (dept_id, dept_name)
values (10, 'コミュニケーションIT事業部');
insert into dept (dept_id, dept_name)
values (11, 'エンタープライズIT事業部');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9010', 1, '須賀尚紀', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9011', 1, 'ジョンスミス', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9012', 1, '川嶋英雄', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9013', 2, '中村恵一', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9014', 2, '住田広', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9015', 3, '落合蒼依', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9016', 3, '皆川富美子', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9017', 4, '江田政利', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9018', 4, '上野莉穂', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9019', 5, '鬼頭南', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9020', 5, '川原菜奈', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9021', 6, '宮原三男', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9022', 6, '長瀬正美', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9023', 7, '川崎富雄', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9024', 7, '三枝芳郎', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9025', 8, '粕谷敏仁', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9026', 8, '藤井信生', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9027', 9, '山下永二', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9028', 9, '朝倉哲雄', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9029', 10, '吉川伍朗', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9030', 10, '玉田雅典', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9031', 11, '吉野孝二', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9032', 11, '塩沢知美', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9033', 11, '蛭田良彦', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('lk2875', 11, '炎帝・浅野（２５）', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');
insert into esq_user (esq_id, dept_id, user_name, password)
values ('li9034', 11, '那須勇大', '$argon2id$v=19$m=4096,t=3,p=1$BP19AuC+ECI28zXNOQkg2A$135kwlQZAhGaqb7H05XTH6Lz9TqbvuSD9KnCC9Whv/E');

insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        1,
        '2016年度 定期健診・人間ドック 受診前問診票',
        4,
        'li9010',
        date_format('16-07-11', '%y-%m-%d'),
        date_format('16-07-12', '%y-%m-%d'),
        date_format('16-07-12', '%y-%m-%d'),
        null,
        2
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        2,
        '2016年度 クラウドコンピューティングの利用に関するアンケート調査',
        4,
        'li9010',
        date_format('16-07-11', '%y-%m-%d'),
        date_format('16-07-14', '%y-%m-%d'),
        date_format('16-07-17', '%y-%m-%d'),
        'クラウドの利用状況調査です。',
        3
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        3,
        'スマートフォン/タブレット/ケータイ利用動向調査2016',
        2,
        'li9010',
        date_format('16-07-11', '%y-%m-%d'),
        null,
        null,
        'モバイル機器の社内での浸透率を調査します。',
        2
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        4,
        '2016年度 裁量労働制に関する社内アンケート',
        1,
        'li9010',
        date_format('16-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        5,
        '情報セキュリティに関する緊急意識調査',
        4,
        'li9010',
        date_format('16-07-11', '%y-%m-%d'),
        date_format('16-07-12', '%y-%m-%d'),
        date_format('16-07-19', '%y-%m-%d'),
        'セキュリティ事故の頻発を受け、セキュリティに関する社員の意識調査を実施します。',
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        6,
        'ビジネススキルアップセミナー(bss)実施に関するアンケート(ビジネスソリューション事業部限定)',
        3,
        'li9010',
        date_format('16-07-11', '%y-%m-%d'),
        date_format('16-07-18', '%y-%m-%d'),
        null,
        'bs内でのbss受講状況を調査します。',
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        7,
        '2013年度 定期健診・人間ドック 受診前問診票',
        1,
        'li9010',
        date_format('13-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        8,
        '2015年度 金融事業部旅行行き先調査',
        1,
        'li9010',
        date_format('15-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        9,
        '2010年度 定期健診・人間ドック 受診前問診票',
        1,
        'li9010',
        date_format('10-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        10,
        '第3四半期 株主総会運営に関するアンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        11,
        '社食の追加メニューに関するアンケート4',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        12,
        '2014年度 金融事業部旅行行き先調査',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        13,
        'cit事業部忘年会の参加確認アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        14,
        '2011年度 定期健診・人間ドック 受診前問診票',
        1,
        'li9010',
        date_format('11-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        15,
        '2011年度 金融事業部旅行行き先調査',
        1,
        'li9010',
        date_format('11-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        16,
        '第1四半期 株主総会運営に関するアンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        17,
        '社食の追加メニューに関するアンケート3',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        18,
        'cit事業部忘年会の参加確認アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        19,
        '第4四半期 株主総会運営に関するアンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        20,
        '金融審議会に関する意識調査',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        21,
        '会議室の環境改善のためのアンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        22,
        '富士登山に関する健康調査',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        23,
        '第2四半期 株主総会運営に関するアンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        24,
        '2014年度後期 icup運営改善アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        25,
        'java8の導入検討アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        26,
        'cit事業部忘年会の参加確認アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        27,
        '社食の追加メニューに関するアンケート2',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        28,
        'cit事業部忘年会の参加確認アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        29,
        '2012年度 定期健診・人間ドック 受診前問診票',
        1,
        'li9010',
        date_format('12-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        30,
        '2014年度前期 icup運営改善アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        31,
        'cit事業部忘年会の参加確認アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        32,
        '社食の追加メニューに関するアンケート1',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        33,
        '社内スターバックス誘致のためのアンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        34,
        'オススメのランチ収集アンケート',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        35,
        '社食の追加メニューに関するアンケート',
        1,
        'li9010',
        date_format('98-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        36,
        '2012年度 金融事業部旅行行き先調査',
        1,
        'li9010',
        date_format('12-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        37,
        '社内サーバ更改に関する意識調査',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        38,
        'バックアップ導入検討調査',
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d'),
        null,
        null,
        null,
        1
    );
insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        39,
        '映画に関するアンケート',
        3,
        'li9010',
        date_format('15-07-17', '%y-%m-%d'),
        date_format('15-07-17', '%y-%m-%d'),
        null,
        null,
        1
    );
    insert into enquete (
        enquete_id,
        enquete_name,
        enquete_state_id,
        create_user_id,
        create_date,
        start_date,
        finish_date,
        enquete_subtext,
        version
    )
values (
        41,
        '職業別ストレスチェック',
        4,
        'li9010',
        date_format('15-07-17', '%y-%m-%d'),
        date_format('15-07-17', '%y-%m-%d'),
         date_format('16-07-19', '%y-%m-%d'),
        null,
        1
    );
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (1, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (2, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (2, 'li9011', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (3, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (3, 'li9012', 1);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (4, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (4, 'li9015', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (4, 'li9020', 1);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (5, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (6, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (7, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (8, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (9, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (10, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (11, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (12, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (13, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (14, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (15, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (16, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (17, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (18, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (19, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (20, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (21, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (22, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (23, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (24, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (25, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (26, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (27, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (28, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (29, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (30, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (31, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (32, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (33, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (34, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (35, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (36, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (37, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (38, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (39, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (41, 'li9010', 0);
insert into enquete_admin_user (enquete_id, esq_id, delete_flag)
values (41, 'lk2875', 0);
insert into enquete_dept (enquete_id, dept_id)
values (1, 1);
insert into enquete_dept (enquete_id, dept_id)
values (1, 2);
insert into enquete_dept (enquete_id, dept_id)
values (1, 3);
insert into enquete_dept (enquete_id, dept_id)
values (1, 4);
insert into enquete_dept (enquete_id, dept_id)
values (1, 5);
insert into enquete_dept (enquete_id, dept_id)
values (1, 6);
insert into enquete_dept (enquete_id, dept_id)
values (1, 7);
insert into enquete_dept (enquete_id, dept_id)
values (1, 8);
insert into enquete_dept (enquete_id, dept_id)
values (1, 9);
insert into enquete_dept (enquete_id, dept_id)
values (1, 10);
insert into enquete_dept (enquete_id, dept_id)
values (1, 11);
insert into enquete_dept (enquete_id, dept_id)
values (2, 1);
insert into enquete_dept (enquete_id, dept_id)
values (2, 2);
insert into enquete_dept (enquete_id, dept_id)
values (2, 3);
insert into enquete_dept (enquete_id, dept_id)
values (2, 4);
insert into enquete_dept (enquete_id, dept_id)
values (2, 5);
insert into enquete_dept (enquete_id, dept_id)
values (2, 6);
insert into enquete_dept (enquete_id, dept_id)
values (2, 7);
insert into enquete_dept (enquete_id, dept_id)
values (2, 8);
insert into enquete_dept (enquete_id, dept_id)
values (2, 9);
insert into enquete_dept (enquete_id, dept_id)
values (2, 10);
insert into enquete_dept (enquete_id, dept_id)
values (2, 11);
insert into enquete_dept (enquete_id, dept_id)
values (3, 2);
insert into enquete_dept (enquete_id, dept_id)
values (4, 3);
insert into enquete_dept (enquete_id, dept_id)
values (5, 5);
insert into enquete_dept (enquete_id, dept_id)
values (6, 8);
insert into enquete_dept (enquete_id, dept_id)
values (7, 1);
insert into enquete_dept (enquete_id, dept_id)
values (8, 1);
insert into enquete_dept (enquete_id, dept_id)
values (9, 1);
insert into enquete_dept (enquete_id, dept_id)
values (10, 1);
insert into enquete_dept (enquete_id, dept_id)
values (11, 1);
insert into enquete_dept (enquete_id, dept_id)
values (12, 1);
insert into enquete_dept (enquete_id, dept_id)
values (13, 1);
insert into enquete_dept (enquete_id, dept_id)
values (14, 1);
insert into enquete_dept (enquete_id, dept_id)
values (15, 1);
insert into enquete_dept (enquete_id, dept_id)
values (16, 1);
insert into enquete_dept (enquete_id, dept_id)
values (17, 1);
insert into enquete_dept (enquete_id, dept_id)
values (18, 1);
insert into enquete_dept (enquete_id, dept_id)
values (19, 1);
insert into enquete_dept (enquete_id, dept_id)
values (20, 1);
insert into enquete_dept (enquete_id, dept_id)
values (21, 1);
insert into enquete_dept (enquete_id, dept_id)
values (22, 1);
insert into enquete_dept (enquete_id, dept_id)
values (23, 1);
insert into enquete_dept (enquete_id, dept_id)
values (24, 1);
insert into enquete_dept (enquete_id, dept_id)
values (25, 1);
insert into enquete_dept (enquete_id, dept_id)
values (26, 1);
insert into enquete_dept (enquete_id, dept_id)
values (27, 1);
insert into enquete_dept (enquete_id, dept_id)
values (28, 1);
insert into enquete_dept (enquete_id, dept_id)
values (29, 1);
insert into enquete_dept (enquete_id, dept_id)
values (30, 1);
insert into enquete_dept (enquete_id, dept_id)
values (31, 1);
insert into enquete_dept (enquete_id, dept_id)
values (32, 1);
insert into enquete_dept (enquete_id, dept_id)
values (33, 1);
insert into enquete_dept (enquete_id, dept_id)
values (34, 1);
insert into enquete_dept (enquete_id, dept_id)
values (35, 1);
insert into enquete_dept (enquete_id, dept_id)
values (36, 1);
insert into enquete_dept (enquete_id, dept_id)
values (37, 1);
insert into enquete_dept (enquete_id, dept_id)
values (38, 1);
insert into enquete_dept (enquete_id, dept_id)
values (39, 1);
insert into enquete_dept (enquete_id, dept_id)
values (41, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (1, 1, 1, 1, 1, '朝食を食べていますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        2,
        1,
        2,
        2,
        0,
        '質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        3,
        1,
        3,
        3,
        0,
        '質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (4, 3, 1, 2, 1, '個人のモバイル端末は何を使っていますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (5, 4, 1, 1, 1, '裁量労働制は継続するべきですか', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        6,
        5,
        1,
        1,
        1,
        '情報区分について認識していますか。',
        '極秘、秘、部内秘、社外秘の区分について説明出来ますか。',
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (7, 6, 1, 1, 1, 'bssを受講したことがありますか', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        8,
        6,
        2,
        1,
        1,
        '質問1「はい」で答えた方のみご回答ください。',
        'bssは現在の業務に活かされていますか',
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        9,
        6,
        3,
        2,
        1,
        '質問1で「いいえ」と答えた方のみご回答ください。',
        'bssを受講しない理由はなんですか',
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        10,
        6,
        4,
        3,
        0,
        'bssを受講するうえで何か要望があれがこちらに記載してください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (11, 2, 1, 1, 1, '現在の案件でクラウドを利用していますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        12,
        2,
        2,
        2,
        0,
        '質問1で「はい」と答えた方のみ：利用したことのあるクラウドサービスはなんですか。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        13,
        7,
        2,
        2,
        0,
        '質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (14, 7, 1, 1, 1, '朝食を食べていますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        15,
        7,
        3,
        3,
        0,
        '質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (16, 8, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        17,
        9,
        3,
        3,
        0,
        '質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        18,
        9,
        2,
        2,
        0,
        '質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (19, 9, 1, 1, 1, '朝食を食べていますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (20, 10, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (21, 11, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (22, 12, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (23, 13, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        24,
        14,
        3,
        3,
        0,
        '質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (25, 14, 1, 1, 1, '朝食を食べていますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        26,
        14,
        2,
        2,
        0,
        '質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (27, 15, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (28, 16, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (29, 17, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (30, 18, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (31, 19, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (32, 20, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (33, 21, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (34, 22, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (35, 23, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (36, 24, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (37, 25, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (38, 26, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (39, 27, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (40, 28, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        41,
        29,
        2,
        2,
        0,
        '質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (
        42,
        29,
        3,
        3,
        0,
        '質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。',
        null,
        1
    );
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (43, 29, 1, 1, 1, '朝食を食べていますか。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (44, 30, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (45, 31, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (46, 32, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (47, 33, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (48, 34, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (49, 35, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (50, 36, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (51, 37, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (52, 38, 1, 1, 1, '一時保存用', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (53, 39, 1, 1, 1, 'どのくらいの頻度で映画館に行きますか', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (54, 39, 2, 2, 0, '好きなジャンルの映画を選択してください', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (55, 39, 3, 3, 0, '当劇場へのご意見をお書きください', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (56, 41, 1, 3, 0, 'ストレスだと感じる場面を200文字以内でお書きください。', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (57, 41, 3, 1, 0, '非常にたくさんの仕事をしなければならない', null, 1);
insert into question (
        question_id,
        enquete_id,
        question_number,
        question_type_id,
        require_flag,
        question_text,
        question_subtext,
        version
    )
values (58, 41, 3, 2, 0, '仕事のストレスの解消方法であてはまるものすべてにチェックを付けてください。', null, 1);

insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (1, 1, 1, 'ほぼ毎日食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (2, 1, 2, 'ときどき食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (3, 1, 3, 'ほとんど食べない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (4, 2, 1, '食べる習慣がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (5, 2, 2, '食べる時間がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (6, 2, 3, 'おなかが空かないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (7, 2, 4, '準備するのが面倒だから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (8, 2, 5, '食べないほうが調子が良いから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (9, 2, 6, 'ダイエットをしているから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (10, 2, 7, 'その他', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (11, 4, 1, 'スマートフォン', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (12, 4, 2, 'タブレット', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (13, 4, 3, 'フィーチャーフォン', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (14, 4, 4, 'スマートウォッチ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (15, 5, 1, '継続するべき', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (16, 5, 2, '撤廃すべき', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (17, 6, 1, '完璧に理解している', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (18, 6, 2, 'ある程度理解している', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (19, 6, 3, 'そんな区分があることは知らない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (20, 7, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (21, 7, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (22, 8, 1, '非常に活かされている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (23, 8, 2, '少し活かされている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (24, 8, 3, 'あまり活かされていない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (25, 8, 4, 'まったく活かされていない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (26, 9, 1, '時間がない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (27, 9, 2, '内容に興味が無い', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (28, 9, 3, 'bssのレベルが高い', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (29, 9, 4, 'bssのレベルが低い', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (30, 9, 5, '上司の許可がもらえない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (31, 11, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (32, 11, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (33, 12, 1, 'cloudis', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (34, 12, 2, 'aws', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (35, 12, 3, 'azure', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (36, 12, 4, 'google app engine', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (37, 12, 5, 'さくらのクラウド', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (38, 12, 6, 'force.com', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (39, 14, 1, 'ほぼ毎日食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (40, 14, 2, 'ときどき食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (41, 14, 3, 'ほとんど食べない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (42, 13, 1, '食べる習慣がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (43, 13, 2, '食べる時間がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (44, 13, 3, 'おなかが空かないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (45, 13, 4, '準備するのが面倒だから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (46, 13, 5, '食べないほうが調子が良いから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (47, 13, 6, 'ダイエットをしているから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (48, 13, 7, 'その他', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (49, 16, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (50, 16, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (51, 19, 1, 'ほぼ毎日食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (52, 19, 2, 'ときどき食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (53, 19, 3, 'ほとんど食べない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (54, 18, 1, '食べる習慣がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (55, 18, 2, '食べる時間がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (56, 18, 3, 'おなかが空かないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (57, 18, 4, '準備するのが面倒だから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (58, 18, 5, '食べないほうが調子が良いから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (59, 18, 6, 'ダイエットをしているから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (60, 18, 7, 'その他', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (61, 20, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (62, 20, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (63, 21, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (64, 21, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (65, 22, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (66, 22, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (67, 23, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (68, 23, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (69, 25, 1, 'ほぼ毎日食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (70, 25, 2, 'ときどき食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (71, 25, 3, 'ほとんど食べない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (72, 26, 1, '食べる習慣がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (73, 26, 2, '食べる時間がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (74, 26, 3, 'おなかが空かないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (75, 26, 4, '準備するのが面倒だから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (76, 26, 5, '食べないほうが調子が良いから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (77, 26, 6, 'ダイエットをしているから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (78, 26, 7, 'その他', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (79, 27, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (80, 27, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (81, 28, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (82, 28, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (83, 29, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (84, 29, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (85, 30, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (86, 30, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (87, 31, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (88, 31, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (89, 32, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (90, 32, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (91, 33, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (92, 33, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (93, 34, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (94, 34, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (95, 35, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (96, 35, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (97, 36, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (98, 36, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (99, 37, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (100, 37, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (101, 38, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (102, 38, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (103, 39, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (104, 39, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (105, 40, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (106, 40, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (107, 43, 1, 'ほぼ毎日食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (108, 43, 2, 'ときどき食べている', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (109, 43, 3, 'ほとんど食べない', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (110, 41, 1, '食べる習慣がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (111, 41, 2, '食べる時間がないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (112, 41, 3, 'おなかが空かないから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (113, 41, 4, '準備するのが面倒だから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (114, 41, 5, '食べないほうが調子が良いから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (115, 41, 6, 'ダイエットをしているから', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (116, 41, 7, 'その他', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (117, 44, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (118, 44, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (119, 45, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (120, 45, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (121, 46, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (122, 46, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (123, 47, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (124, 47, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (125, 48, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (126, 48, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (127, 49, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (128, 49, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (129, 50, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (130, 50, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (131, 51, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (132, 51, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (133, 52, 1, 'はい', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (134, 52, 2, 'いいえ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (135, 53, 1, '週に一度', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (136, 53, 2, '月に一度', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (137, 53, 3, '年に一度かそれ以外', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (138, 54, 1, 'アクション', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (139, 54, 2, 'サスペンス', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (140, 54, 3, 'ドラマ', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (141, 54, 4, 'コメディー', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (142, 54, 5, 'ラブストーリー', 1);
insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (143, 57, 1, 'そうだ', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (144, 57, 2, 'まあそうだ', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (145, 57, 3, '違う', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (146, 58, 1, '趣味を楽しむ', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (147, 58, 2, 'バランスの良い食事をとる', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (148, 58, 3, '十分な睡眠をとる', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (149, 58, 4, '暴飲暴食をする', 1);insert into choice (
        choice_id,
        question_id,
        choice_number,
        choice_text,
        version
    )
values (150, 58, 5, '運動', 1);
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        1,
        1,
        'li9011',
        date_format('14-07-11', '%y-%m-%d')
    );

insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        2,
        1,
        'li9012',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        3,
        1,
        'li9010',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        4,
        1,
        'li9026',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        5,
        2,
        'li9011',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        6,
        2,
        'li9012',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        7,
        2,
        'li9013',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        8,
        2,
        'li9026',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        9,
        2,
        'li9020',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        10,
        2,
        'li9010',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        11,
        2,
        'li9031',
        date_format('14-07-11', '%y-%m-%d')
    );
    insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        12,
        41,
        'li9010',
        date_format('14-07-11', '%y-%m-%d')
    );insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        13,
        41,
        'li9011',
        date_format('14-07-11', '%y-%m-%d')
    );insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        14,
        41,
        'lk2875',
        date_format('14-07-11', '%y-%m-%d')
    );insert into enquete_answer (
        enquete_answer_id,
        enquete_id,
        esq_id,
        answer_date
    )
values (
        15,
        41,
        'li9034',
        date_format('14-07-11', '%y-%m-%d')
    );
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (1, 1, 1, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (2, 1, 2, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (3, 1, 3, '昼まで起きないから');
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (4, 2, 1, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (5, 2, 2, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (6, 2, 3, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (7, 3, 1, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (8, 3, 2, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (9, 3, 3, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (10, 4, 1, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (11, 4, 2, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (12, 4, 3, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (13, 5, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (14, 5, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (15, 6, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (16, 6, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (17, 7, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (18, 7, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (19, 8, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (20, 8, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (21, 9, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (22, 9, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (23, 10, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (24, 10, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (25, 11, 11, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (26, 11, 12, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (27, 12, 56, 'ストレスを感じたことがないからわからない');
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (28, 12, 57, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (29, 12, 58, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (30, 13, 56, '人手不足で一人にかかる負担が大きく、肉体的、精神的にかなりキツい');
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (31, 13, 57, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (32, 13, 58, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (33, 14, 56, 'チームメンバーのレベルが低すぎてフォローが大変な時');
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (34, 14, 57, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (35, 14, 58, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (36, 15, 56, '人が目の前にいるのに殴れない時');
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (37, 15, 57, null);
insert into question_answer (
        question_answer_id,
        enquete_answer_id,
        question_id,
        answer_text
    )
values (38, 15, 58, null);

insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (1, 1, 3);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (2, 2, 4);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (3, 2, 5);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (4, 2, 6);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (5, 2, 7);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (6, 2, 8);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (7, 2, 9);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (8, 2, 10);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (9, 4, 3);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (10, 5, 4);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (11, 5, 5);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (12, 5, 6);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (13, 5, 7);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (14, 5, 8);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (15, 5, 9);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (16, 7, 1);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (17, 10, 3);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (18, 11, 4);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (19, 13, 31);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (20, 14, 33);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (21, 14, 34);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (22, 14, 35);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (23, 15, 31);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (24, 16, 34);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (25, 16, 37);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (26, 16, 38);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (27, 17, 31);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (28, 18, 33);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (29, 18, 34);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (30, 18, 35);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (31, 19, 31);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (32, 20, 34);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (33, 21, 32);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (34, 23, 31);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (35, 24, 33);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (36, 24, 34);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (37, 24, 35);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (38, 25, 31);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (39, 26, 33);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (40, 26, 35);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (41, 26, 37);

insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (43, 28, 144);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (45, 29, 146);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (46, 29, 147);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (47, 29, 148);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (48, 29, 149);

insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (51, 31, 145);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (52, 32, 146);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (53, 32, 147);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (54, 32, 148);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (55, 32, 149);

insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (56, 34, 143);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (59, 35, 146);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (60, 35, 147);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (61, 35, 148);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (62, 35, 149);


insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (65, 37, 145);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (66, 38, 146);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (67, 38, 147);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (68, 38, 148);
insert into choice_answer (choice_answer_id, question_answer_id, choice_id)
values (69, 38, 149);
commit;
