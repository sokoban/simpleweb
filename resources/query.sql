-- :name insert-comment :! :n
insert into locations (x, y)
values (:x, :y)

-- :name find-comments :? :*
select * from locations where id = ?

-- :name get-all-locations :? :*
select id, x, y from locations
