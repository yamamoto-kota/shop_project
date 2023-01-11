select
  /*%expand*/*
from
  question
where
  enquete_id = /* enqueteId */1
order by question_number asc