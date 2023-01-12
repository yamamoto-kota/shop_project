select
  /*%expand*/*
from
  question
where
  question_id = /* questionId */1
  and
  version = /* version */1
