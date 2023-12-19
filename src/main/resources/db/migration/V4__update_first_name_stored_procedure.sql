-- Create function update all first name to uppercase
CREATE OR REPLACE FUNCTION update_first_name_to_upper_case()
RETURNS void AS
$$
BEGIN
      UPDATE "users_tbl" SET "first_name" = UPPER("first_name");
END;
$$ LANGUAGE plpgsql;

-- Execute function
SELECT update_first_name_to_upper_case();

-- Delete function
DROP FUNCTION IF EXISTS update_first_name_to_uppercase();

