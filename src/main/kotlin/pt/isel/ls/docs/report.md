# Phase 1

## Introduction

This document contains the relevant design and implementation aspects of LS project's first phase.

## Modeling the database

### Conceptual model ###

The following diagram holds the Entity-Relationship model for the information managed by the system.

![img.png](img.png)

We highlight the following aspects:

- Type of each attribute: This is important because in our work we defined each atribuite with a class that we created, this was done to make the code more readable and to make it easier to understand the code.
- Cardinality of each relationship: This is important because it allows us to understand how the tables are related to each other and how the information is stored in the database.
- ADICIONAR MAIS


The conceptual model has the following restrictions:

- Data Integrity: We need to ensure that each field is not null, and that the data is consistent with the type of the field.
- Relationship Integrity: We need to ensure that the relationships between the domains are consistent, and that the data is consistent with the relationships.
- Security: We need to ensure that the data is secure and that only authorized users can access the data needing a token that is unique to each user.
- Error handling: We need to ensure that the data is handled correctly and that the user is informed of any errors that may occur.

### Physical Model ###

The physical model of the database is available in (_link to the SQL script with the schema definition_).

We highlight the following aspects of this model:

* (_include a list of relevant design issues_)

## Software organization

### Open-API Specification ###

(_include a link to the YAML file containing the Open-API Specification_)
https://app.swaggerhub.com/apis/afonsosantos-d07/courts-api/1.0.0

In our Open-API specification, we highlight the following aspects:

(_include a list of relevant issues or details in your specification_)

### Request Details

(_describe how a request goes through the different elements of your solution_)

(_describe the relevant classes/functions used internally in a request_)

(_describe how and where request parameters are validated_)

### Connection Management

(_describe how connections are created, used and disposed_, namely its relation with transaction scopes).

### Data Access

(_describe any created classes to help on data access_).

(_identify any non-trivial used SQL statements_).

### Error Handling/Processing

(_describe how errors are handled and their effects on the application behavior_).

## Critical Evaluation

(_enumerate the functionality that is not concluded and the identified defects_)

(_identify improvements to be made on the next phase_)